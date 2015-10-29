package logic.generator

class SerieDescriptor(title: String,
                       theme: String,
                       initNbrSeasons: Int,
                       initNbrEpisodes: Int
                       )
  extends Descriptor(title, theme) {

  val nbrSeasons = initNbrSeasons
  val nbrEpisodes = initNbrEpisodes

}