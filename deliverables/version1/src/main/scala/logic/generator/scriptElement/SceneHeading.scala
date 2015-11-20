package logic.generator.scriptElement

import logic.tools.IntExt
import logic.tools.IntExt.IntExt

case class SceneHeading(intExt: IntExt, location: String, timeOfDay: String) extends ScriptElement {
  override def getString: String = intExt + " - " + location + " - " + timeOfDay
}
