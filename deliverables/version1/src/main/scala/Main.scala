import java.io.{FileOutputStream, ObjectOutputStream}

import logic.Project
import logic.generator.{GraphSerieGenerator, SceneGenerator, SerieDescriptor, OeuvreGenerator}

object Main {
  def main(args: Array[String]) {
    val descriptor = new SerieDescriptor("Poca", "Horreur", 2, 2)
    new GraphSerieGenerator().generate(descriptor)
    val project = Project(descriptor, "test")
    Project.save(project)
    Project.load("").oeuvre.export()
  }
}
