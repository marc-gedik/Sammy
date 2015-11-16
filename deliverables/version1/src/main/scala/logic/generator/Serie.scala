package logic.generator

import java.io.{File, PrintWriter}

import logic.generator.scriptElement.{Action, CharacterName, Dialogue, Parenthetical, SceneHeading}

import scala.collection.immutable.::
import scala.collection.mutable.ListBuffer

class Serie(descriptor: SerieDescriptor) extends Oeuvre(descriptor) {
  var separation = ListBuffer[ListBuffer[Int]]()

  def newSeason() =  separation += ListBuffer.empty[Int]
  private def seasonNumber(season: Int) = separation(season-1)

  def newEpisode(season: Int, scenes: Int) = seasonNumber(season) += scenes

  def getEpisode(_season: Int, episode: Int) = {
    val season = seasonNumber(_season)
    var i = 0
    var somme = 0

    for (x <- season if i < episode - 1) {
      i += 1
      somme += x
    }

    new Episode(scenes.slice(somme, somme + season(episode - 1)))
  }

  def export(path: String): Unit = {
    val writer = new PrintWriter(new File(path))
    var (season, episode, k) = (0, 0, 0)

    writer.write("Season 1, Episode 1\n")

    scenes.foreach {
      scene =>

        k += 1
        if (k >= separation(season)(episode)) {
          k = 0
          episode += 1
          writer.write("\n\n Season " + (season + 1) + ", Episode " + (episode + 1) + "\n")
          if (episode >= descriptor.nbrEpisodes(season)) {
            episode = 0
            season += 1
          }
        }

        scene.scriptElements.foreach {
          _ match {
            case a: Action =>
              writer.write("\t\t" + a.getString() + "\n\n")
            case c: CharacterName =>
              writer.write("\t\t\t\t\t" + c.getString() + "\n")
            case d: Dialogue =>
              writer.write("\t\t\t" + d.getString() + "\n\n")
            case p: Parenthetical =>
              writer.write("\t\t\t\t\t" + p.getString() + "\n")
            case sh: SceneHeading =>
              writer.write("\t\t" + sh.getString() + "\n\n")
          }
        }
    }
    writer.close()
  }
}