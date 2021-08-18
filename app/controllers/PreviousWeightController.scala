package controllers

import akka.http.scaladsl.model.HttpResponse
import com.google.inject.Inject
import connectors.UserConnector
import models.PreviousWeight
import play.api.http.Writeable
import play.api.libs.json.{JsArray, JsBoolean, JsValue, Json, Writes}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.PreviousWeightService

import java.time.LocalDate
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

    previousWeightService.addNewWeight(username, 150.0).map { data =>
      Created(Json.toJson(data))
    }
  }
}
