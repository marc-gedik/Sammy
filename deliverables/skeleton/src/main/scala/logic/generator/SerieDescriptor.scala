package logic.generator

class SerieDescriptor(title: String,
                      theme: String,
                      val nbrSeasons: Int,
                      val nbrEpisodes: Int
                       )
  extends Descriptor(title, theme)