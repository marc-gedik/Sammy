package logic.generator

import logic.generator.scriptElement.SceneHeading
import org.scalatest.{FlatSpec, Matchers}

class SceneTest extends FlatSpec with Matchers{

  "Scene" should "Start with SceneHeading" in {
    val serie = OeuvreGenerator("Serie").generate(new SerieDescriptor("Titre", "Theme", 1, 1))

    val scriptElement = serie.scenes.head.scriptElements.head

    scriptElement should be (asInstanceOf[SceneHeading])
  }

  "Scene" should "character -> dialogue" in {
    //TODO
  }
}
