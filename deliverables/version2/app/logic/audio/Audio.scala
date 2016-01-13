package logic.audio

abstract class Audio {

  def addText(text : String): Unit

  /*
    Export text to mp3
   */
  def export(path: String): Unit
}

object Audio {

  def apply() = new Audio {
    var text = ""

    override def addText(textPart: String): Unit = text += textPart

    /*
    Export text to mp3
   */
    override def export(path: String): Unit = Freetts_syn.syn(text, path)
  }
}