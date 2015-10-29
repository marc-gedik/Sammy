package logic.generator

import logic.tools.Sex.Sex
import logic.tools.Role.Role

class Character(
                 initName: String,
                 initAge: Int,
                 initSex: Sex,
                 initRole: Role
                 ) {
  val name = initName
  val age = initAge
  val sex = initSex
  val role = initRole
}

