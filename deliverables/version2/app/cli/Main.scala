package cli

import java.io.File

import logic.Project


object Main {


  val ui = UI
  val types = List(Choice("Serie", ui.serie))

  def main(args: Array[String]): Unit = {

    // Command Line Argument Parser (Scopt)
    val parser = new scopt.OptionParser[Config]("") {
      opt[File]('o', "open") valueName ("<file>") action { (x, c) =>
        c.copy(project = x)
        c.copy(openProject = true)
      } text ("out is a required file property")
      opt[Unit]("debug") hidden() action { (_, c) =>
        c.copy(debug = true)
      } text ("this option is hidden in the usage text")
      note("some notes.\n")
      help("help") text ("prints this usage text")
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        val project =
          if (config.openProject)
            Project.load(config.project)
          else {
            println("Choose the type of the oeuvre")
            Project(ui.choices(types))
          }
        ui.menu(project)

      case None =>
      // arguments are bad, error message will have been displayed
    }
  }
}
