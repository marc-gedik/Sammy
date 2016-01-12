package logic.casting

import logic.generator.Character

trait ICasting {
  def associate(character: Character, actor: Actor)
}
