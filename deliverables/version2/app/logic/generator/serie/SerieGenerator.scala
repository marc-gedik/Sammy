package logic.generator

abstract class SerieGenerator extends OeuvreGenerator {
  override type O = Serie
  override type D = SerieDescriptor

  def numberOfScenePerEpisode(season: Int, episode: Int) = 5

}