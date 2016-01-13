package logic.tools

import scala.io.{BufferedSource, Source}

object Utils {

  var getResource: (String => BufferedSource) =
    fileName => Source.fromFile("conf/resources/" + fileName)

  var getResourcePath: (String => String) =
    fileName => "conf/resources/" + fileName
}
