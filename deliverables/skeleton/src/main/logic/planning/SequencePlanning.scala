package logic.planning

import java.util.Date

import logic.generator.Scene

import scala.collection.mutable.ListBuffer

class SequencePlanning(initScene : Scene) {
  var dateTournage : Date = null
  var lieu : String = null
  var done : Boolean = false
  var intervenants = ListBuffer.empty[Intervenant]

  val scene = initScene
}
