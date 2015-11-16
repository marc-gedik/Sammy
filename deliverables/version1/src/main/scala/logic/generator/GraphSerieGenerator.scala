package logic.generator

import java.io.{File, PrintWriter}

import logic.generator.constraint.Constraint
import logic.generator.scriptElement._
import logic.tools.Sex
import logic.tools.Sex.Sex

import scala.io.Source
import scala.util.Random

import scala.collection.mutable.ListBuffer

import net.liftweb.json.{Printer, JsonAST, JsonParser}

import scalax.collection.edge.LUnDiEdge
import scalax.collection.io.json
import scalax.collection.io.json.descriptor.NodeDescriptor
import scalax.collection.io.json.descriptor.predefined.{LDi, LUnDi}
import scalax.collection.io.json.{JsonGraph, JsonGraphCoreCompanion}
import scalax.collection.mutable.Graph
import scalax.collection.edge.Implicits._ // shortcuts

class GraphSerieGenerator extends SerieGenerator {
  private val precedence = "0"
  private val mutualExclusion = "1"

  //TODO import graph from file
  private val descriptor = getDescriptor()
  //private val graph = Graph((StarterNode() ~+> FinisherNode())(precedence), ((ScriptElementNode(0, Dialogue("ok")) ~+ FinisherNode())(mutualExclusion)))
  //exportGraph(true)
  private val graph = importGraph()

  def getDescriptor() = {
    val statrterDesc = new NodeDescriptor[StarterNode](typeId = "Starters") {
      def id(node: Any) = node match {
        case StarterNode() => "S"
      }
    }
    val finisherDescriptor = new NodeDescriptor[FinisherNode](typeId = "Finishers") {
      def id(node: Any) = node match {
        case FinisherNode() => "F"
      }
    }
    val scriptElementDescriptor = new NodeDescriptor[ScriptElementNode](typeId = "ScriptElements", extraClasses = List(classOf[Dialogue])) {
      def id(node: Any) = node match {
        case ScriptElementNode(id, _) => id.toString
      }
    }

    new json.descriptor.Descriptor[Node](
      defaultNodeDescriptor = statrterDesc,
      defaultEdgeDescriptor = LUnDi.descriptor("UnDi"),
      namedNodeDescriptors = Seq(finisherDescriptor, scriptElementDescriptor),
      namedEdgeDescriptors = Seq(LDi.descriptor("Di")))
  }

  def importGraph(): Graph[Node, LUnDiEdge] = {
    val jsonText = Source.fromFile("src/main/ressources/graph").mkString
    new JsonGraphCoreCompanion(Graph).fromJson(jsonText, descriptor)
  }

  def exportGraph(pretty: Boolean = false) = {
    val writer = new PrintWriter(new File("src/main/ressources/graph"))
    val jsonText = new JsonGraph(graph).toJson(descriptor)
    val json =
      if (pretty)
        Printer.pretty(JsonAST.render(JsonParser.parse(jsonText)))
      else
        jsonText
    writer.write(json)
    writer.close()
  }

  implicit val edgeFactory = scalax.collection.edge.LDiEdge

  def updateGraph(root: graph.NodeT): Unit = {
    // mutual exclusions
    val mutualGraph = root.outgoing.filter(node => node.label.equals(mutualExclusion))

    // Insert a link between each direct predecessor and each direct successor of excluded event
    for (edge <- mutualGraph) {
      val node = edge.to
      // TODO recursivement: updateGraph(node)
      for (pre <- node.diPredecessors)
        for (suc <- node.diSuccessors)
          graph.addLEdge(pre, suc)(precedence)
    }

    // Remove all edge
    for (edge <- mutualGraph)
      graph - edge.to
  }


  // Generate a character
  def generateCharacter(sex: Sex.Sex): Character = new Character(???, ???, sex, ???)

  def generateCharacter(): Character =
    if (Random.nextBoolean())
      generateCharacter(Sex.M)
    else
      generateCharacter(Sex.F)

  def generateCharacter(sex: Option[Sex]): Character = sex match {
    case Some(sex) => generateCharacter(sex)
    case None => generateCharacter()
  }


  def generate(desc: SerieDescriptor): Serie = {
    var root: graph.NodeT = graph.get(StarterNode())
    val characters = ListBuffer.empty[Character]
    val serie = new Serie(desc)
    var scene = new Scene

    var finished = false
    while (!finished) {
      val options = root.outgoing.withFilter(edge => edge.label == precedence)

      val num = Random.nextInt(options.size)

      root = graph.get(options.toVector(num).to)

      root.value match {
        case ScriptElementNode(_, SceneHeading(intExt, location, timeOfDay)) =>
          if (!scene.scriptElements.isEmpty) serie.add(scene)
          scene = new Scene
          scene.add(SceneHeading(intExt, location, timeOfDay))
        case ScriptElementNode(_, x) =>
          scene.add(x)
        case IntroduceCharacterNode(sex) =>
          val character = generateCharacter(sex)
          characters += character
          serie.add(character)
        case FinisherNode() =>
          finished = true
      }

      updateGraph(root)
    }
    serie
  }

  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = oeuvre
}

sealed trait Node extends Product with Serializable

case class StarterNode() extends Node
case class ScriptElementNode(val id: Int, val value: ScriptElement) extends Node
case class IntroduceCharacterNode(val sex: Option[Sex]) extends Node
case class FinisherNode() extends Node