package logic.generator

import java.io.{File, PrintWriter}

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

  override def export(path: String): Unit = {
    val writer = new PrintWriter(new File(path))

    writer.write("Season 1, Episode 1\n")

    for ((act, i) <- acts.zipWithIndex) {
      writer.write("Act " + (i + 1) + "\n\n")
      act.foreach(scene => writer.write(scene toString))
    }
    writer.close()
  }

}
