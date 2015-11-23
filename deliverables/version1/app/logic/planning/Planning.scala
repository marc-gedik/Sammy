package logic.planning

import logic.generator.Oeuvre

import scala.collection.mutable.ListBuffer

class Planning(oeuvre: Oeuvre) extends Serializable{
  val sequencePlannings = ListBuffer.empty[SequencePlanning]

  oeuvre.scenes.foreach(scene => sequencePlannings += new SequencePlanning(scene))

}
