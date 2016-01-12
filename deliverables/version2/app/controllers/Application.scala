package controllers

import java.io.File
import javax.inject.Inject

import logic.generator.SerieDescriptor
import logic.tools.Utils
import logic.{Project, SerieProject}
import play.api.Play
import play.api.Play.current
import play.api.cache.Cache
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.io.Source


class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  Utils.getResource = fileName => Source.fromInputStream(Play.resourceAsStream("/resources/" + fileName).get)
  Utils.getResourcePath = fileName => Play.resource("/resources/" + fileName).get.getPath

  val descriptorForm = Form(mapping(
    "title" -> nonEmptyText,
    "theme" -> default(nonEmptyText, "comedie"),
    "seasons" -> number(min = 1),
    "episodes" -> number(min = 1))
  (SerieDescriptor.apply)(SerieDescriptor.unapply))

  def index = Action {
    Cache.get("project") match {
      case Some(project) => Ok(views.html.project())
      case None => Ok(views.html.index(descriptorForm))
    }
  }

  def createProject = Action { implicit request =>
    descriptorForm.bindFromRequest().fold(
      formWithErrors =>
        BadRequest(views.html.index(formWithErrors)),
      descriptor => {
        val serieProject = Project(descriptor)
        Cache.set("project", serieProject)
        Ok(views.html.project())
      }
    )
  }

  def scenario = Action {
    Cache.get("project") match {
      case Some(project) => Ok(views.html.scenario())
      case None => Ok(views.html.index(descriptorForm))
    }
  }

  def season(season: Int) = Action {
    Cache.getAs[Project with SerieProject]("project") match {
      case Some(project) => {
        if (project.oeuvre.exist(season, 0, 0))
          Ok(views.html.season(season))
        else
          Ok(views.html.scenario())
      }
      case None => Ok(views.html.index(descriptorForm))
    }
  }

  def episode(season: Int, episode: Int) = Action {
    Cache.getAs[Project with SerieProject]("project") match {
      case Some(project) => {
        if (project.oeuvre.exist(season, episode, 0))
          Ok(views.html.episode(season, episode))
        else
          Ok(views.html.scenario())
      }
      case None => Ok(views.html.index(descriptorForm))
    }
  }

  def scene(season: Int, episode: Int, scene: Int) = Action {
    Cache.getAs[Project with SerieProject]("project") match {
      case Some(project) => {
        if (project.oeuvre.exist(season, episode, scene))
          Ok(views.html.scene(season = season)(episode = episode)(scene = scene))
        else
          Ok(views.html.scenario())
      }
      case None => Ok(views.html.index(descriptorForm))
    }
  }

  def preflight(all: String) = Action {
    Ok("").withHeaders(
      ("Access-Control-Allow-Origin", "*"),
      ("Allow", "*"),
      ("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS"),
      ("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent")
    )
  }

  def load = Action(parse.multipartFormData) { request =>
    println(request.body.files)
    val file = request.body.file("file").get
    val project = Project.load(file.ref.file)
    Cache.set("project", project)
    Ok("File uploaded")
  }

  def save = Action {
    Cache.getAs[Project with SerieProject]("project") match {
      case Some(project) => {
        Project.save(project, "/tmp/")
        Ok.sendFile(new File("/tmp/" + project.oeuvre.descriptor.title))
      }
      case None => Ok(views.html.index(descriptorForm))
    }
  }
}

