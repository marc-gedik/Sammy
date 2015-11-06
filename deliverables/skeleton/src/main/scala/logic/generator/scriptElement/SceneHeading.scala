package logic.generator.scriptElement

import logic.tools.IntExt.IntExt

class SceneHeading(val intExt: IntExt, val location: String, val timeOfDay: String) extends ScriptElement {
  override def getString(): String = intExt + " - " + location + " - " + timeOfDay
}
