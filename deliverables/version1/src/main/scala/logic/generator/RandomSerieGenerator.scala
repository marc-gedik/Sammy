package logic.generator

import logic.generator.constraint.Constraint

class RandomSerieGenerator extends SerieGenerator {

  def randomGenerator(descriptor: SerieDescriptor): Serie = {
    val serie = new Serie(descriptor)
    for (season <- 1 to descriptor.nbrSeasons) {
      serie.newSeason()
      for (episode <- 1 to descriptor.nbrEpisodes(season)) {
        val scenes = numberOfScenePerEpisode(season, episode)
        serie.newEpisode(season, scenes)
        for (_ <- 1 to scenes) {
          serie.add(RandomSceneGenerator.generate(descriptor))
        }
      }
    }
    serie
  }

  def generate(desc: SerieDescriptor): Serie = randomGenerator(desc)

  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = oeuvre

}
