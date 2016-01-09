package logic.generator

import scala.collection.mutable.ListBuffer


class Season extends Container[Episode] {

  def nbEpisode: Int = length
}