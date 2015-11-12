package logic.generator

import logic.generator.constraint.Constraint
import logic.generator.scriptElement._
import logic.tools.Sex
import logic.tools.Sex.Sex

import scala.collection.mutable.ListBuffer
import scala.util.Random
import scalax.collection.edge.Implicits._
import scalax.collection.mutable.Graph


class GraphSerieGenerator extends SerieGenerator {
  private val precedence = 0
  private val mutualExclusion = 1

  //TODO import graph from file
  private val graph = Graph((StarterNode ~+> FinisherNode)(precedence), ((ScriptElementNode(Dialogue("ok")) ~+ FinisherNode)(mutualExclusion)))

  implicit val edgeFactory = scalax.collection.edge.LDiEdge

  def updateGraph(root: graph.NodeT): Unit = {
    // mutual exclusions
    val mutualGraph = root.outgoing.filter(node => node.label.equals(mutualExclusion))

    // Insert a link between each direct predecessor and each direct successor of excluded event
    for (edge <- mutualGraph) {
      val node = edge.to
      updateGraph(node)
      for (pre <- node.diPredecessors)
        for (suc <- node.diSuccessors)
          graph.addLEdge(pre, suc)(precedence)
    }

    // Remove all edge
    for (edge <- mutualGraph)
      graph - edge.to
  }

  def generateCharacter(): Character =
    if (Random.nextBoolean())
      generateCharacter(Sex.M)
    else
      generateCharacter(Sex.F)

  def generateCharacter(sex: Sex.Sex): Character = new Character(???, ???, sex, ???)

  def generateCharacter(sex: Option[Sex]): Character = sex match {
    case Some(sex) => generateCharacter(sex)
    case None => generateCharacter()
  }

  def generate(desc: SerieDescriptor): Serie = {
    var root: graph.NodeT = graph.get(StarterNode)
    val characters = ListBuffer.empty[Character]
    val serie = new Serie(desc)
    var scene = new Scene

    var finished = false
    while (!finished) {

      val options = root.outgoing.withFilter(edge => edge.label.equals(precedence))
      val num = Random.nextInt(options.size)

      root = graph.get(options.toVector(num).to)

      root.value match {
        case ScriptElementNode(SceneHeading(intExt, location, timeOfDay)) =>
          if (!scene.scriptElements.isEmpty) serie.add(scene)
          scene = new Scene
          scene.add(SceneHeading(intExt, location, timeOfDay))
        case ScriptElementNode(x) =>
          scene.add(x)
        case IntroduceCharacterNode(sex) =>
          val character = generateCharacter(sex)
          characters += character
          serie.add(character)
        case FinisherNode =>
          finished = true
      }

      updateGraph(root)
    }
    serie
  }

  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = oeuvre
}

trait Node

case object StarterNode extends Node
case class ScriptElementNode(val x: ScriptElement) extends Node
case class IntroduceCharacterNode(val sex: Option[Sex]) extends Node
case object FinisherNode extends Node