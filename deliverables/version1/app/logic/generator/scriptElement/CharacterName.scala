package logic.generator.scriptElement

import logic.generator.Character

case class CharacterName(character: String) extends ScriptElement {
  override def getString: String = character
}
