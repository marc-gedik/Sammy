package logic.audio

import java.io.File

import scala.collection.mutable.ListBuffer

abstract class Audio {

  def addText(text : String): Unit

  /*
    Export text to mp3
   */
  def export(): File
}

object Audio {

  def apply() = new Audio {
    var text = ListBuffer.empty[String]

    override def addText(textPart: String): Unit = text += textPart

    /*
    Export text to mp3
   */
    //TODO
    override def export(): File = ???
  }
}