package logic.generator

import scala.collection.mutable.ListBuffer

abstract class Oeuvre(val descriptor: Descriptor) {

  var scenes = List[Scene]()
  var characters = List[Character]()

  def add(character: Character) = characters = characters :+ character

  def add(scene: Scene) = scenes = scenes :+ scene

  def export(path: String = "src/main/ressources/script.txt"): Unit
}