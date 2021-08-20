package controllers

import com.google.inject.Inject
import helpers.ErrorHandler
import models.MacroStatRequest
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
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
    def macroStatRequest: MacroStatRequest = content.map { data =>
      val activityLevel = (data \ "activityLevel").as[String]
      val setGoal = (data \ "setGoal").as[Double]
      val proteinPreference = (data \ "proteinPreference").as[Int]
      val fatPreference = (data \ "fatPreference").as[Int]
      val carbPreference = (data \ "carbPreference").as[Int]
      val bodyFat = (data \ "bodyFat").as[Double]
      val equationPreference = (data \ "equationPreference").as[String]
      val maintenanceCalories = (data \ "maintenanceCalories").as[Int]
      val targetCalories = (data \ "targetCalories").as[Int]
      val timeToGoal = (data \ "timeToGoal").as[Int]

      MacroStatRequest(
        activityLevel,
        setGoal,
        Some(proteinPreference),
        Some(fatPreference),
        Some(carbPreference),
        Some(bodyFat),
        Some(equationPreference),
        maintenanceCalories,
        targetCalories,
        timeToGoal
      )
    }.get

    macroStatService.addNewMacroStat(username, macroStatRequest).map { data =>
      Created(Json.toJson(data))
    }
  }
}
