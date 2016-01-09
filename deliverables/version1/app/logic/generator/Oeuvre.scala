package logic.generator

import scala.collection.mutable.ListBuffer

abstract class Oeuvre extends Serializable {
  val descriptor: Descriptor

  val scenes = ListBuffer.empty[Scene]
  val characters = ListBuffer.empty[Character]

  def add(character: Character) = characters += character

  def add(scene: Scene) : Unit = scenes += scene

  /**
   * Export the oeuvre to the specified location
   *
   * @param path where the file will be saved
   */
  def export(path: String = "src/main/resources/script.txt"): Unit
}