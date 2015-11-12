package logic.generator

import logic.generator.constraint.Constraint
import org.scalatest.fixture

import scala.collection.mutable.ListBuffer
import scala.util.Random
import scalax.collection.mutable.Graph
import scalax.collection.GraphEdge._
import scalax.collection.edge.Implicits._

class GraphSerieGenerator extends SerieGenerator {
  private val precedence = 0
  private val mutualExclusion = 1

  private val graph = Graph[Node, UnDiEdge]((Node("ok", 0) ~+ Node("ok", 0))(mutualExclusion))

  implicit val edgeFactory = scalax.collection.GraphEdge.DiEdge

  def updateGraph(root: graph.NodeT): Unit = {
    // mutual exclusions
    val mutualGraph = root.outgoing.filter(node => node.label.equals(mutualExclusion))

    // Insert a link between each direct predecessor and each direct successor of excluded event
    for (edge <- mutualGraph) {
      val node = edge.to
      updateGraph(node)
      for (pre <- node.diPredecessors)
        for (suc <- node.diSuccessors)
          graph +~=(pre, suc)
    }

    // Remove all edge
    for (edge <- mutualGraph)
      graph - edge.to
  }


  def generate(desc: SerieDescriptor): Serie = {
    var root: graph.NodeT = graph.get(new Node("start", 0))
    val character = ListBuffer.empty[Character]
    while (true) {

      val options = root.outgoing.withFilter(edge => edge.label.equals(precedence))
      val num = Random.nextInt(options.size)

      root = graph.get(options.toVector(num).to)

      // TODO Add root node to story

      updateGraph(root)
    }
    ???
  }

  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = oeuvre
}

case class Node(val value: String, val kind: Int)

