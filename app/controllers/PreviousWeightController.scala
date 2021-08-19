package controllers

import com.google.inject.Inject
import play.api.Logging
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.PreviousWeightService

import scala.concurrent.{ExecutionContext, Future}

class PreviousWeightController @Inject()(previousWeightService: PreviousWeightService, cc: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController with Logging {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllPreviousWeights(username: String): Action[AnyContent] = Action.async { implicit request =>

    previousWeightService.findPreviousWeights(username) match {
      case Some(value) =>
        val json = value.map {
          data => Json.arr(data)
        }
        json.map { data =>
          logger.info(data.toString)
          Ok(data)
        }
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }

  def findLastPreviousWeight(username: String): Action[AnyContent] = Action.async { implicit request =>

    previousWeightService.findLastWeight(username) match {
      case Some(value) =>
        val json = value.map {
          data => Json.arr(data.maxBy(_.dateTime))
        }
        json.map { data =>
          logger.info(data.toString)
          Ok(data)
        }
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }

  def addNewWeight(username: String): Action[AnyContent] = Action.async { implicit request =>

    val content = request.body.asJson

    //TODO Add getOrElse
    val weight = content.map { data =>
      (data \ "weight").as[Double]
    }.get

    println(weight)

    previousWeightService.addNewWeight(username, weight).map {
      case 1 => Created(Json.toJson(2))
      case 200 => Created(Json.toJson(200))
      case 400 => BadRequest(Json.toJson(400))
    }
  }
}
