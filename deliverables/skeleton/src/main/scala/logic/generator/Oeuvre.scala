package logic.generator

import scala.collection.mutable.ListBuffer

abstract class Oeuvre(val descriptor: Descriptor) {

  val scenes = ListBuffer.empty[Scene]
  val characters = ListBuffer.empty[Character]

  def add(character: Character) = characters += character

  def add(scene: Scene) = scenes += scene

  def export(): Unit
}