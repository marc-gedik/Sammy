package logic.generator.scriptElement

trait ScriptElement {

  def getString(): String
  override def toString() = getString()
}