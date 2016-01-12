package logic.audio

import java.io.File

trait Audioizable {
  def exportAudio(): File
}
