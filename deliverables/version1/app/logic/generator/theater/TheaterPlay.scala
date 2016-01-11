package logic.generator

import scala.collection.mutable.ListBuffer

class TheaterPlay(val descriptor: TheaterPlayDescriptor) extends Oeuvre with Iterable[Act] {

  private val acts = new ListBuffer[Act]

  override def iterator: Iterator[Act] = acts.iterator

  override def add(scene: Scene) = {
    super.add(scene)
    val act = acts.last
    act += scene
  }

  def newAct = acts += new Act

  override def export(path: String): Unit = ???

}
