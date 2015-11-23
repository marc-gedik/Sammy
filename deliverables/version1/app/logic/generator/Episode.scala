package logic.generator

import scala.collection.mutable.ListBuffer

class Episode extends Iterable[Scene] with Serializable{

  private val scenes = ListBuffer.empty[Scene]
  def apply(scene: Int) = scenes(scene)

  def += (scene: Scene) = scenes += scene


  override def iterator: Iterator[Scene] = scenes.iterator

}
