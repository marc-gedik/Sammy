package logic.generator.scriptElement

import logic.generator.Character

case class CharacterName(val character: String) extends ScriptElement {
  override def getString(): String = character
}
