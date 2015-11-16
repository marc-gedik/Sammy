package generator.constraint

import logic.generator.constraint.Constraint
import logic.generator.{Character, Scene}

class KillCharacter(scene: Scene, val character: Character) extends Constraint(scene)