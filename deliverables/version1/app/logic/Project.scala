package logic

import java.io._

import logic.casting.Casting
import logic.generator._
import logic.planning.Planning

trait SerieProject {
  type OeuvreType = Serie
  val oeuvre: Serie
}

class Project(descriptor: Descriptor) extends Serializable {
  val oeuvre = OeuvreGenerator(descriptor)
  val planning = new Planning(oeuvre)
  val casting = new Casting(oeuvre)
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
    new ObjectInputStream(new FileInputStream(path)) {
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

  def loadFromString(obj: String): Project = {
    new ObjectInputStream(new ByteArrayInputStream(obj.getBytes)) {
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
