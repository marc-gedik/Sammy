package logic.generator

import logic.generator.scriptElement.SceneHeading
import org.scalatest.{FlatSpec, Matchers}

class SceneGeneratorTest extends FlatSpec with Matchers {

  val serie : Serie = OeuvreGenerator(new SerieDescriptor("Titre", "Theme", 1, 1))

  "Scene" should "be not empty" in {
    serie.scenes.foreach(scene => scene.scriptElements should not be empty)
  }

  "Scene" should "Start with SceneHeading" in {
    serie.scenes.foreach(scene => scene.scriptElements.head should be(asInstanceOf[SceneHeading]))
  }

  "Scene" should "Have each character followed by a dialogue" in {
    //TODO
  }

  "Scene" should "Have each parenthetical preceded by a character and followed by a dialogue" in {
    //TODO
  }
}
