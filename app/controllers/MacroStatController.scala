package controllers

import com.google.inject.Inject
import helpers.ErrorHandler
import models.MacroStat
import play.api.Logging
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Result}
import services.MacroStatService

import scala.concurrent.{ExecutionContext, Future}

class MacroStatController @Inject()(macroStatService: MacroStatService, cc: ControllerComponents, controllerErrorHandler: ErrorHandler)(implicit val ec: ExecutionContext) extends BaseController with Logging {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllMacroStats(username: String): Action[AnyContent] = Action.async { implicit request =>

    val result = macroStatService.findMacroStats(username)

    controllerErrorHandler.macroStatErrorHandler(result)
  }

  def addNewMacroStat(username: String): Action[AnyContent] = Action.async { implicit request =>

    val content = request.body.asJson

    def macroStatData: Option[MacroStat] = content.map(data => data.as[MacroStat])

    if (macroStatData.isEmpty) {
      Future.successful(BadRequest("Missing parameters in request"))
    } else {
      macroStatService.addNewMacroStat(username, macroStatData.get) match {
        case Some(value) => value.map { data =>
          Created(Json.toJson(data))
        }
        case None =>
          logger.warn("NoContent")
          Future.successful(NoContent)
      }
    }
  }
}

//val macroStatData: Option[MacroStat] = content.map { data =>
//  MacroStat(
//  activityLevel = (data \ "activityLevel").as[String],
//  setGoal = (data \ "setGoal").as[Double],
//  proteinPreference = Some((data \ "proteinPreference").as[Int]),
//  fatPreference = Some((data \ "fatPreference").as[Int]),
//  carbPreference = Some((data \ "carbPreference").as[Int]),
//  bodyFat = Some((data \ "bodyFat").as[Double]),
//  equationPreference = Some((data \ "equationPreference").as[String]),
//  maintenanceCalories = (data \ "maintenanceCalories").as[Int],
//  targetCalories = (data \ "targetCalories").as[Int],
//  timeToGoal = (data \ "timeToGoal").as[Int]
//  )
//  }