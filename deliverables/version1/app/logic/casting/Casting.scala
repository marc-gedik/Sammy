package logic.casting

import logic.generator.{Oeuvre, Character}

class Casting(oeuvre: Oeuvre) extends ICasting with Serializable{
  private val assoc: Map[Character, Actor] = Map()

  oeuvre.characters.foreach(character => assoc + (character -> ???))

  def associate(character: Character, actor: Actor): Unit = assoc + (character -> actor)

}
