package logic.casting

class Casting extends ICasting {
  private val actors: List[Actor] = List()
  private val assoc: Map[Role, Actor] = Map()

  def associate(role: Role, actor: Actor): Unit = assoc + (role -> actor)
}
