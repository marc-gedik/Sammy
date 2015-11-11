package logic.generator.scriptElement

class Parenthetical(val parenthetical: String) extends ScriptElement {
  override def getString(): String = parenthetical
}