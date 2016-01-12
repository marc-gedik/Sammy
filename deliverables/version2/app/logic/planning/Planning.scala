package logic.planning

import logic.generator.Oeuvre

import scala.collection.mutable.ListBuffer

class Planning(oeuvre: Oeuvre) extends Serializable{
  val sequencePlannings = ListBuffer.empty[SequencePlanning]

  /*
    Ajout d'un planning vide pour chaque sequence de l'oeuvre
   */
  oeuvre.scenes.foreach(scene => sequencePlannings += new SequencePlanning(scene))

}
