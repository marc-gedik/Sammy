import logic.generator.{SceneGenerator, SerieDescriptor, OeuvreGenerator}

object Main {
  def main(args: Array[String]) {
    OeuvreGenerator("serie").generate(new SerieDescriptor("Poca", "Horreur", 1, 1)).export()
  }
}
