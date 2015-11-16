package logic.generator

import logic.generator.scriptElement.ScriptElement

import scala.collection.mutable.ListBuffer

class Scene {
  val scriptElements = ListBuffer.empty[ScriptElement]

  def add(scriptElement: ScriptElement) = scriptElements += scriptElement
}