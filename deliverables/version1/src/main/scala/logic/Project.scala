package logic

import java.io._

import logic.casting.Casting
import logic.generator._
import logic.planning.Planning

trait SerieProject {
  type OeuvreType = Serie
  val oeuvre: Serie
}

class Project(descriptor: Descriptor, val name: String) extends Serializable {
  val oeuvre = OeuvreGenerator(descriptor)
  val planning = new Planning(oeuvre)
  val casting = new Casting(oeuvre)
}

object Project {
  def apply(descriptor: Descriptor, name: String): Project = {
    descriptor match {
      case _: SerieDescriptor => new Project(descriptor, name) with SerieProject
    }
  }

  def save(project: Project, path: String = "src/main/resources/projects/"): Unit = {
    new File(path).mkdirs
    val oos = new ObjectOutputStream(new FileOutputStream(path + project.name))
    oos.writeObject(project)
    oos.close
  }

  def load(name: String, path: String = "src/main/resources/projects/"): Project = {
    new ObjectInputStream(new FileInputStream(path + name)){
      override def resolveClass(desc: java.io.ObjectStreamClass): Class[_] = {
        try { Class.forName(desc.getName, false, getClass.getClassLoader) }
        catch { case ex: ClassNotFoundException => super.resolveClass(desc) }
      }
    }.readObject match {
      case project : Project => project
    }
  }


}
