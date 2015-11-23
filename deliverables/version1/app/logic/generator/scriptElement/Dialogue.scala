package logic.generator.scriptElement

case class Dialogue(dialogue: String) extends ScriptElement {
  override def getString: String = dialogue
}