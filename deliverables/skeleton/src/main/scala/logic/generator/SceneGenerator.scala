package logic.generator

import logic.generator.scriptElement._
import logic.tools.IntExt

import scala.io.Source
import scala.util.Random

trait SceneGenerator {
  def generate(descriptor: Descriptor): Scene
}
object RandomSceneGenerator extends SceneGenerator {

  var scenes = List[Scene]()

  Source.fromFile("src/main/ressources/test.txt").getLines().foreach {
    line =>
      val cleaned = line.substring(2)
      line.charAt(0) match {
        case 'H' =>
          scenes = new Scene() :: scenes
          val intExt = if (cleaned.substring(0, 3) == "INT") IntExt.Ext else IntExt.Int
          val s = cleaned.substring(5).split(" - ")
          val place = s(0)
          val time = s(1)
          scenes.head.add(new SceneHeading(intExt, place, time))
        case 'A' =>
          scenes.head.add(new Action(cleaned))
        case 'C' =>
          scenes.head.add(new CharacterName(cleaned))
        case 'P' =>
          scenes.head.add(new Parenthetical(cleaned))
        case 'D' =>
          scenes.head.add(new Dialogue(cleaned))
      }
  }

  def generate(descriptor: Descriptor): Scene = {
    val _scenes = Random.shuffle(scenes)
    //scenes = _scenes.tail
    _scenes.head
  }
}