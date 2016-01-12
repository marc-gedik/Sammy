package logic.generator

class SerieDescriptor(
  val title: String,
  val theme: String,
  val nbrSeasons: Int,
  private val _nbrEpisodes: Int
)
  extends Descriptor {

  def nbrEpisodes(season: Int) = _nbrEpisodes

}

object SerieDescriptor {
  def apply(title: String, theme: String, season: Int, episodes: Int): SerieDescriptor = {
    new SerieDescriptor(title, theme, season, episodes)
  }

  def unapply(serieDescriptor: SerieDescriptor): Option[(String, String, Int, Int)] = {
    Option(serieDescriptor.title, serieDescriptor.theme, serieDescriptor.nbrSeasons, serieDescriptor._nbrEpisodes)
  }
}
