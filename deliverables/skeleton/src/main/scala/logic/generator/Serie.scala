package logic.generator

class Serie(descriptor: SerieDescriptor) extends Oeuvre(descriptor) {
  var separation = List[List[Int]]()

  def getEpisode(season: Int, episode: Int) = {
    val seasonTmp = separation(season - 1)
    var i = 0
    var somme = 0

    for (x <- seasonTmp if i < episode - 1) {
      i += 1
      somme += x
    }

    new Episode(scenes.slice(somme, somme + seasonTmp(episode - 1)))
  }

  def export(path : String): Unit = ???
}

