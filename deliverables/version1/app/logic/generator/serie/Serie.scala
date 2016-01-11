package logic.generator

import java.io.{File, PrintWriter}

import logic.audio.{Audio, Audioizable}
import logic.generator.scriptElement.Dialogue

import scala.collection.mutable.ListBuffer

class Serie(val descriptor: SerieDescriptor) extends Oeuvre with Iterable[Season] with Audioizable {


  private val seasons = new ListBuffer[Season]

  def apply(season: Int) = seasons(season)

  def newSeason() = seasons += new Season

  private def seasonNumber(season: Int) = seasons(season - 1)

  def exist(season: Int, episode: Int, scene: Int): Boolean = {
    season >= 0 && episode >= 0 && scene >= 0 &&
      seasons.length >= season && seasons(season).nbEpisode >= episode && seasons(season)(episode).toList.length >= scene
  }

  def newEpisode(season: Int) = seasonNumber(season) += new Episode

  def getEpisode(season: Int, episode: Int) = seasonNumber(season - 1)(episode - 1)

  override def add(scene: Scene) = {
    super.add(scene)
    val episode: Episode = seasons.last.last
    episode += scene
  }

  def export(path: String): Unit = {
    val writer = new PrintWriter(new File(path))

    writer.write("Season 1, Episode 1\n")

    for (season <- 1 to seasons.length)
      for (episode <- 1 to seasons(season - 1).nbEpisode)
        for (scene <- seasons(season - 1)(episode - 1).iterator) {
          writer.write("Season " + season + ", Episode " + episode + "\n\n")

          writer.write(scene.toString)

        }
    writer.close()
  }

  override def iterator: Iterator[Season] = seasons.iterator

  override def exportAudio(): File = {
    val audio = Audio()
    scenes.foreach(scene => scene foreach {case Dialogue(dialogue) => audio.addText(dialogue)})
    audio.export()
  }
}
