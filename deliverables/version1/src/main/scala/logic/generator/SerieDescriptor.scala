package logic.generator

class SerieDescriptor(
  val title: String,
  val theme: String,
  val nbrSeasons: Int,
  _nbrEpisodes: Int
)
  extends Descriptor {

  def nbrEpisodes(season: Int) = _nbrEpisodes

}