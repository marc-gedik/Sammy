package logic.generator.scriptElement

class Dialogue(val dialogue: String) extends ScriptElement {
  override def getString(): String = dialogue
}