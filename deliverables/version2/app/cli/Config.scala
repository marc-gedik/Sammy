package cli

import java.io.File

/**
 * Created by Marc on 12/01/2016.
 */
case class Config(openProject: Boolean = false, project: File = null, debug: Boolean = false)
