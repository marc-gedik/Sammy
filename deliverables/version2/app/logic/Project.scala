package logic

import java.io._

import logic.casting.Casting
import logic.generator._
import logic.planning.Planning

trait SerieProject extends Project{
  type OeuvreType = Serie
  override val oeuvre: Serie =
    OeuvreGenerator(descriptor) match {
      case serie: Serie => serie
    }
}

abstract class Project(val descriptor: Descriptor) extends Serializable {
  val oeuvre : Oeuvre

  lazy val planning = new Planning(oeuvre)
  lazy val casting = new Casting(oeuvre)
}

object Project {
  def apply(descriptor: Descriptor): Project = {
    descriptor match {
      case _: SerieDescriptor => new Project(descriptor) with SerieProject
    }
  }

  def save(project: Project, path: String = "src/main/resources/projects/"): Unit = {
    new File(path).mkdirs
    val oos = new ObjectOutputStream(new FileOutputStream(path + project.oeuvre.descriptor.title))
    oos.writeObject(project)
    oos.close
  }

  def save(project: Project): String = {
    val bo = new ByteArrayOutputStream
    val so = new ObjectOutputStream(bo)
    so.writeObject(project)
    so.flush()
    bo.toString()
  }

  def load(path: String): Project = {
    load(new File(path))
  }

  def load(file: File): Project = {
    new ObjectInputStream(new FileInputStream(file)) {
      override def resolveClass(desc: java.io.ObjectStreamClass): Class[_] = {
        try {
          Class.forName(desc.getName, false, getClass.getClassLoader)
        }
        catch {
          case ex: ClassNotFoundException => super.resolveClass(desc)
        }
      }
    }.readObject match {
      case project: Project => project
    }
  }

}
