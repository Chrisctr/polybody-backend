package controllers

import com.google.inject.Inject
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.PreviousWeightService

import scala.concurrent.{ExecutionContext, Future}

class PreviousWeightController @Inject()(previousWeightService: PreviousWeightService, cc: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllPreviousWeights(username: String): Action[AnyContent] = Action.async { implicit request =>

    val cursor = previousWeightService.findPreviousWeights(username)

    val futureWeightJsonArray: Future[JsArray] =
      cursor.map { weights => Json.arr(weights) }

    futureWeightJsonArray.map { weights =>
      Ok(weights)
    }
  }

  def findLastPreviousWeight(username: String): Action[AnyContent] = Action.async { implicit request =>

    val cursor = previousWeightService.findLastWeight(username)

    val futureWeightJsonArray: Future[JsArray] =
      cursor.map { weights => Json.arr(weights.maxBy(_.dateTime)) }

    futureWeightJsonArray.map { weights =>
      Ok(weights)
    }
  }

  def addNewWeight(username: String): Action[AnyContent] = Action.async { implicit request =>

    val content = request.body.asJson

    val weight = content.map { data =>
      (data \ "weight").as[Double]
    }.get

    println(weight)

    previousWeightService.addNewWeight(username, weight).map { data =>
      Created(Json.toJson(data))
    }
  }
}
