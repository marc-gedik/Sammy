package logic.generator

import java.io.{FileInputStream, File, PrintWriter}

import logic.generator.constraint.Constraint
import logic.generator.scriptElement._
import logic.tools.Sex
import logic.tools.Sex.Sex
import play.api.Play.current
import play.api.Play

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
import scalax.collection.edge.Implicits._

// shortcuts

class GraphSerieGenerator extends SerieGenerator {
  private val precedence = "0"
  private val mutualExclusion = "1"

  private val descriptor = getDescriptor
  //private val graph = Graph((StarterNode() ~+> FinisherNode())(precedence), ((IntroduceCharacterNode(0, "M") ~+ FinisherNode())(mutualExclusion)))
  //exportGraph(true)
  private val graph = importGraph()

  def getDescriptor = {
    val starterDesc = new NodeDescriptor[StarterNode](typeId = "Starters") {
      def id(node: Any) = node match {
        case StarterNode() => "S"
      }
    }
    val finisherDescriptor = new NodeDescriptor[FinisherNode](typeId = "Finishers") {
      def id(node: Any) = node match {
        case FinisherNode() => "F"
      }
    }
    val dialogueNodeDescriptor = new NodeDescriptor[DialogueNode](typeId = "Dialogues") {
      override def id(node: Any): String = node match {
        case DialogueNode(id, _, _, _) => id.toString
      }
    }

    val sceneHeadingDescriptor = new NodeDescriptor[SceneHeadingNode](typeId = "SceneHeadings",
      extraClasses = List(classOf[SceneHeading])) {
      override def id(node: Any): String = node match {
        case SceneHeadingNode(id, _) => id.toString
      }
    }

    val actionDescriptor = new NodeDescriptor[ActionNode](typeId = "Actions") {
      override def id(node: Any): String = node match {
        case ActionNode(id, _) => id.toString
      }
    }

    val introduceCharacter = new NodeDescriptor[IntroduceCharacterNode](typeId = "Characters") {
      override def id(node: Any): String = node match {
        case IntroduceCharacterNode(id, _) => id.toString
      }
    }

    new json.descriptor.Descriptor[Node](
      defaultNodeDescriptor = starterDesc,
      defaultEdgeDescriptor = LUnDi.descriptor("UnDi"),
      namedNodeDescriptors =
        Seq(finisherDescriptor,
          sceneHeadingDescriptor,
          actionDescriptor,
          dialogueNodeDescriptor,
          introduceCharacter
        ),
      namedEdgeDescriptors = Seq(LDi.descriptor("Di")))
  }

  def importGraph(): Graph[Node, LUnDiEdge] = {
    val jsonText = Source.fromInputStream(Play.resourceAsStream("/resources/graph").get).mkString
    new JsonGraphCoreCompanion(Graph).fromJson(jsonText, descriptor)
  }

  def exportGraph(pretty: Boolean = false) = {
    val writer = new PrintWriter(Play.resource("/resources/graph").get.getPath)
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
      for (pre <- node.diPredecessors)
        for (suc <- node.diSuccessors)
          graph.addLEdge(pre, suc)(precedence)
    }

    // Remove all edge
    for (edge <- mutualGraph)
      graph - edge.to
  }


  // Generate a character
  def generateCharacter(sex: Sex.Sex): Character = new Character(???, ???, sex)

  def generateCharacter(): Character =
    if (Random.nextBoolean())
      generateCharacter(Sex.M)
    else
      generateCharacter(Sex.F)

  def generateCharacter(sex: String): Character = sex match {
    case "M" => generateCharacter(Sex.M)
    case "F" => generateCharacter(Sex.F)
    case _ => generateCharacter()
  }

  def generate(desc: SerieDescriptor): Serie = {
    var root: graph.NodeT = graph.get(StarterNode())
    val characters = ListBuffer.empty[Character]
    val serie = new Serie(desc)
    var scene = new Scene

    var finished = false
    while (!finished) {
      val options = root.outgoing

      val num = Random.nextInt(options.size)

      root = graph.get(options.toVector(num).to)

      root.value match {
        case SceneHeadingNode(_, sceneHeading) =>
          scene = new Scene
          serie.add(scene)
          scene.add(sceneHeading)
        case ActionNode(_, action) =>
          for (id <- characters.indices)
            action.replaceAll("$" + id, characters(id).name)
          scene.add(Action(action))
        case DialogueNode(id, characterId, parenthetical, dialogue) =>
          scene.add(CharacterName(characters(characterId).name))
          parenthetical match {
            case "" => ()
            case parenthetical => scene.add(Parenthetical(parenthetical))
          }
          scene.add(Dialogue(dialogue))
        case IntroduceCharacterNode(_, sex) =>
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

case class SceneHeadingNode(id: Int, sceneHeading: SceneHeading) extends Node

case class ActionNode(id: Int, action: String) extends Node

case class DialogueNode(id: Int, characterId: Int, parenthetical: String, dialogue: String) extends Node

case class IntroduceCharacterNode(id: Int, sex: String) extends Node

case class FinisherNode() extends Node