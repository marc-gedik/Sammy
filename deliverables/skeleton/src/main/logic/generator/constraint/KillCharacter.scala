package generator.constraint

import logic.generator.Scene
import logic.generator.constraint.Constraint
import logic.generator.scriptElement.CharacterName

class KillCharacter(scene: Scene, initCharacter: CharacterName) extends Constraint(scene) {
  val character = initCharacter
}