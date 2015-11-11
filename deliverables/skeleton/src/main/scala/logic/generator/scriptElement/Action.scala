package logic.generator.scriptElement

class Action(val action: String) extends ScriptElement {
  override def getString(): String = action
}