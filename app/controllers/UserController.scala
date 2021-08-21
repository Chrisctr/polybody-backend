package controllers

import com.google.inject.Inject

import helpers.ErrorHandler
import play.api.Logging
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.UserService

import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(userService: UserService, cc: ControllerComponents, controllerErrorHandler: ErrorHandler)(implicit val ec: ExecutionContext) extends BaseController with Logging {
  override protected def controllerComponents: ControllerComponents = cc

  def findSpecificUser(username: String): Action[AnyContent] = Action.async { implicit request =>

    val result = userService.findSpecificUser(username)

    controllerErrorHandler.userErrorHandler(result)
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
