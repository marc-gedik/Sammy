package logic.generator.constraint

import logic.generator.{Scene, Character}

class NewCharacter (scene: Scene, initCharacter: Character) extends Constraint(scene) {
  val character = initCharacter
}