package logic.generator.scriptElement

abstract class ScriptElement {

  def getString: String

  override def toString = getString
}