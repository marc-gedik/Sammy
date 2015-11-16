import logic.generator.{GraphSerieGenerator, SceneGenerator, SerieDescriptor, OeuvreGenerator}

object Main {
  def main(args: Array[String]) {
    val descriptor = new SerieDescriptor("Poca", "Horreur", 2, 2)
    OeuvreGenerator("Serie").generate(descriptor).export()
    new GraphSerieGenerator().generate(descriptor)
  }
}
