import logic.generator.{SceneGenerator, SerieDescriptor, OeuvreGenerator}

object Main {
  def main(args: Array[String]) {
    OeuvreGenerator("Serie").generate(new SerieDescriptor("Poca", "Horreur", 2, 2)).export()
  }
}
