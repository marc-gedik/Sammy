package logic.generator

import logic.generator.constraint.Constraint

class SerieGenerator extends OeuvreGenerator {
  type O = Serie
  type D = SerieDescriptor

  def randomGenerator(descriptor: SerieDescriptor): Serie = {
    val numberOfScenePerEpisode = 5
    val serie = new Serie(descriptor)
    for (_ <- 1 to descriptor.nbrSeasons * descriptor.nbrEpisodes * numberOfScenePerEpisode)
          serie.add(SceneGenerator.generate(descriptor))
    serie
  }

<<<<<<< HEAD
  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = null


=======
  def generate(desc: SerieDescriptor): Serie = randomGenerator(desc)

  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = oeuvre
>>>>>>> adb515e1b7f208f4726a76cdf690d0fea979e6bc
}