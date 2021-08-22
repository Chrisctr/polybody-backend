package controllers

import com.google.inject.Inject
import helpers.ErrorHandler
import models.MacroStatRequest
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Result}
import services.MacroStatService

import scala.concurrent.{ExecutionContext, Future}

class MacroStatController @Inject()(macroStatService: MacroStatService, cc: ControllerComponents, controllerErrorHandler: ErrorHandler)(implicit val ec: ExecutionContext) extends BaseController {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllMacroStats(username: String): Action[AnyContent] = Action.async { implicit request =>

    val result = macroStatService.findMacroStats(username)

    controllerErrorHandler.macroStatErrorHandler(result)
  }

  def findLastMacroStat(username: String): Action[AnyContent] = Action.async { implicit request =>

    val result = macroStatService.findLastMacroStat(username)

    controllerErrorHandler.macroStatErrorHandler(result)
  }

  def addNewMacroStat(username: String): Action[AnyContent] = Action.async { implicit request =>

    val content = request.body.asJson

    //TODO Add getOrElse
    def macroStatRequest: Option[MacroStatRequest] = content.map { data =>
      //      val activityLevel = (data \ "activityLevel").as[String]
      //      val setGoal = (data \ "setGoal").as[Double]
      //      val proteinPreference = (data \ "proteinPreference").as[Int]
      //      val fatPreference = (data \ "fatPreference").as[Int]
      //      val carbPreference = (data \ "carbPreference").as[Int]
      //      val bodyFat = (data \ "bodyFat").as[Double]
      //      val equationPreference = (data \ "equationPreference").as[String]
      //      val maintenanceCalories = (data \ "maintenanceCalories").as[Int]
      //      val targetCalories = (data \ "targetCalories").as[Int]
      //      val timeToGoal = (data \ "timeToGoal").as[Int]

      data.as[MacroStatRequest]

    }



    if (macroStatRequest.isEmpty) {
      Future.successful(BadRequest("Missing parameters in request"))
    } else {
      macroStatService.addNewMacroStat(username, macroStatRequest.get).map { data =>
        Created(Json.toJson(data))
      }
    }
  }
}
