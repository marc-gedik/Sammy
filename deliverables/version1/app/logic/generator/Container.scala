package logic.generator

import scala.collection.mutable.ListBuffer

class Container[T] extends Iterable[T] with Serializable {
  private val container = ListBuffer.empty[T]

  def length: Int = container.length

  def apply(i: Int) = container(i)

  def +=(item: T) = container += item


  override def iterator: Iterator[T] = container.iterator

}
