package controllers

import java.io.{FileOutputStream, File, ObjectOutputStream}
import javax.inject.Inject

import logic.{SerieProject, Project}
import logic.generator.{Episode, SerieDescriptor}
import play.api.cache.Cache
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import play.api.Play.current


class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

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

  def load = Action { implicit request =>
    Ok("Salut")
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

