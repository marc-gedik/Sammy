package logic.generator

import logic.generator.scriptElement._

import scala.collection.mutable.ListBuffer

class Scene extends Serializable with Iterable[ScriptElement] {
  val scriptElements = ListBuffer.empty[ScriptElement]

  def add(scriptElement: ScriptElement) = scriptElements += scriptElement

  override def toString = {
    scriptElements.foldLeft[String](""){(s , scriptElement) =>
    s +
    (scriptElement match {
    case a: Action =>
      "\t\t" + a + "\n\n"
    case c: CharacterName =>
      "\t\t\t\t\t" + c + "\n"
    case d: Dialogue =>
      "\t\t\t" + d + "\n\n"
    case p: Parenthetical =>
      "\t\t\t\t\t" + p + "\n"
    case sh: SceneHeading =>
      "\t\t" + sh + "\n\n"
  }
  )
  }
  }

  override def iterator: Iterator[ScriptElement] = scriptElements.iterator
}