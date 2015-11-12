package logic.generator.scriptElement

case class Dialogue(val dialogue: String) extends ScriptElement {
  override def getString(): String = dialogue
}