package logic.generator.scriptElement

case class Parenthetical(parenthetical: String) extends ScriptElement {
  override def getString: String = parenthetical
}