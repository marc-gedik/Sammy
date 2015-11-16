package logic.generator

class SerieDescriptor(title: String,
                      theme: String,
                      val nbrSeasons: Int,
                      _nbrEpisodes: Int
                       )
  extends Descriptor(title, theme) {

  def nbrEpisodes(season: Int) = _nbrEpisodes
}