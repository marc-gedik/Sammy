package logic.generator

import logic.generator.scriptElement.ScriptElement

import scala.collection.mutable.ListBuffer

class Scene extends Serializable{
  val scriptElements = ListBuffer.empty[ScriptElement]

  def add(scriptElement: ScriptElement) = scriptElements += scriptElement
}