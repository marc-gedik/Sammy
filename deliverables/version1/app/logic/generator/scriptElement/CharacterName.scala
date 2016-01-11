package logic.generator.scriptElement

case class CharacterName(character: String) extends ScriptElement {
  override def getString: String = character
}
