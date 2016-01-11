package logic.generator

import logic.generator.scriptElement._
import logic.tools.IntExt
import play.api.Play
import play.api.Play.current

import scala.io.Source
import scala.util.Random

abstract class SceneGenerator {
  def generate(descriptor: Descriptor): Scene
}

object RandomSceneGenerator extends SceneGenerator {

  val serieScenes = load("test.txt")

  def load(file: String): List[Scene] = {
    var list = List[Scene]()
    Source.fromInputStream(Play.resourceAsStream("/resources/" + file).get).getLines().foreach {
      line =>
        val cleaned = line.substring(2)
        line.charAt(0) match {
          case 'H' =>
            list = new Scene() :: list
            val intExt = if (cleaned.substring(0, 3) == "INT") IntExt.Ext else IntExt.Int
            val s = cleaned.substring(5).split(" - ")
            val place = s(0)
            val time = s(1)
            list.head.add(new SceneHeading(intExt, place, time))
          case 'A' =>
            list.head.add(new Action(cleaned))
          case 'C' =>
            list.head.add(new CharacterName(cleaned))
          case 'P' =>
            list.head.add(new Parenthetical(cleaned))
          case 'D' =>
            list.head.add(new Dialogue(cleaned))
        }
    }
    list
  }

  def generate(descriptor: Descriptor): Scene = {
    val data =
      descriptor match {
        case _: SerieDescriptor => serieScenes
      }
    val _scenes = Random.shuffle(data)
    //scenes = _scenes.tail
    _scenes.head
  }
}