package logic.generator.scriptElement

case class Action(val action: String) extends ScriptElement {
  override def getString(): String = action
}