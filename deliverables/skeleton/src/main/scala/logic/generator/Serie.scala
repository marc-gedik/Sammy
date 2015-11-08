package logic.generator

import java.io.{File, PrintWriter}

import logic.generator.scriptElement.{Action, CharacterName, Dialogue, Parenthetical, SceneHeading}

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

  def export(path : String): Unit = {
    val writer = new PrintWriter(new File(path))
    val numberOfScenePerEpisode = 5
    var (i, j, k) = (1, 1, 1)

    scenes.foreach{
      scene =>
        writer.write("\n Season "+i+", Episode "+j+", Scene "+k+"\n")
        k += 1
        if (k > numberOfScenePerEpisode) {
          k = 1
          j += 1
          if (j > descriptor.nbrEpisodes) {
            j = 1
            i += 1
          }
        }
        scene.scriptElements.foreach{
          _ match {
            case a : Action =>
              writer.write("\t\t\t"+a.getString()+"\n")
            case c : CharacterName =>
              writer.write("\t\t\t\t\t"+c.getString()+"\n")
            case d : Dialogue =>
              writer.write("\t\t\t"+d.getString()+"\n")
            case p : Parenthetical =>
              writer.write("\t\t\t\t\t"+p.getString()+"\n")
            case sh : SceneHeading =>
              writer.write("\t\t"+sh.getString()+"\n")
          }
        }
    }
    writer.close()
  }
}

