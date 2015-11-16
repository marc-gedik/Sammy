package logic.generator.scriptElement

case class Parenthetical(val parenthetical: String) extends ScriptElement {
  override def getString(): String = parenthetical
}