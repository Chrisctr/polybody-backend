package controllers

import com.google.inject.Inject
import helpers.ErrorHandler
import play.api.Logging
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.PreviousWeightService

import scala.concurrent.{ExecutionContext, Future}

class PreviousWeightController @Inject()(previousWeightService: PreviousWeightService, cc: ControllerComponents, controllerErrorHandler: ErrorHandler)(implicit val ec: ExecutionContext) extends BaseController with Logging {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllPreviousWeights(username: String): Action[AnyContent] = Action.async { implicit request =>

    val result = previousWeightService.findPreviousWeights(username)

    controllerErrorHandler.previousWeightErrorHandler(result)
  }

  def addNewWeight(username: String): Action[AnyContent] = Action.async { implicit request =>

    val content = request.body.asJson

    val weight: Option[Double] = content.map { data =>
      (data \ "weight").as[Double]
    }

    if (weight.isEmpty) {
      Future.successful(BadRequest("Missing parameters in request"))
    } else {
      previousWeightService.addNewWeight(username, weight.get).map {
        case Some(result) => Created
        case None => Created
      }
    }
  }
}
