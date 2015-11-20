package logic.generator.scriptElement

case class Action(action: String) extends ScriptElement {
  override def getString: String = action
}