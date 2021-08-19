package controllers

import com.google.inject.Inject
import config.MongoConfiguration
import models.{MacroStat, PreviousWeight, User}
import play.api.http.Writeable
import play.api.libs.json.{JsArray, JsObject, JsValue, Json, Writes}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request, Result}
import reactivemongo.api.Cursor
import connectors.UserConnector
import play.api.Logging
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.api.commands.WriteResult
import services.UserService

import java.time.LocalDate
import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(userService: UserService, cc: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController with Logging {
  override protected def controllerComponents: ControllerComponents = cc

  def findSpecificUser(username: String): Action[AnyContent] = Action.async { implicit request =>

    userService.findSpecificUser(username) match {
      case Some(value) =>
        val userJson = value.map {
          user => Json.arr(user)
        }
        userJson.map { user =>
          Ok(user)
        }
      case None =>
        logger.warn("BadRequest")
        Future.successful(BadRequest("BadRequest"))
    }
  }


//  /// Add user param later
//  def createUser(): Action[AnyContent] = Action.async { implicit request =>
//    val formData: User = User.form.bindFromRequest.get
//
//    val result = userConnector.createTestUser(formData)
//
//    result.map(_ => Ok)
//  }
//


}
