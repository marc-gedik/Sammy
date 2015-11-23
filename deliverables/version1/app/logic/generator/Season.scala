package logic.generator

import scala.collection.mutable.ListBuffer


class Season extends Iterable[Episode] with Serializable {


  private val episodes = ListBuffer.empty[Episode]

  def apply(i: Int) = episodes(i)

  def nbEpisode: Int = episodes.length

  def += (episode: Episode) = episodes += episode

  override def iterator: Iterator[Episode] = episodes.iterator
}