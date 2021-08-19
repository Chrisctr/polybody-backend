package controllers

import com.google.inject.Inject
import config.MongoConfiguration
import models.{MacroStat, PreviousWeight, User}
import play.api.http.Writeable
import play.api.libs.json.{JsArray, JsObject, JsValue, Json, Writes}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request, Result}
import reactivemongo.api.Cursor
import connectors.UserConnector
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.api.commands.WriteResult

import java.time.LocalDate
import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(userConnector: UserConnector, cc: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController {
  override protected def controllerComponents: ControllerComponents = cc

  def findSpecificUser(username: String): Action[AnyContent] = Action.async { implicit request =>

    val cursor = userConnector.findSpecificUser(username)

    val futureUserJsonArray: Future[JsArray] =
      cursor.map { user => Json.arr(user.head) }

    futureUserJsonArray.map { user =>
      Ok(user)
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
