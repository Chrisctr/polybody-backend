package controllers

import com.google.inject.Inject
import models.User
import play.api.http.Writeable
import play.api.libs.json.{JsArray, JsObject, Json, Writes}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import reactivemongo.api.Cursor
import service.UserService

import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(userService: UserService, cc: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllUsers: Action[AnyContent] = Action.async { implicit request =>

    val cursor = userService.findAllUsers("test")

    val futureUserJsonArray: Future[JsArray] =
      cursor.map { user => Json.arr(user) }

    futureUserJsonArray.map { user =>
      Ok(user)
    }



  }
}
