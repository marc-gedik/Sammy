package logic.generator

abstract class SerieGenerator extends OeuvreGenerator {
  type O = Serie
  type D = SerieDescriptor

  def numberOfScenePerEpisode(season: Int, episode: Int) = 5

}