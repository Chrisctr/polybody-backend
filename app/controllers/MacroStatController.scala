package controllers

import com.google.inject.Inject
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.{MacroStatService, PreviousWeightService}

import scala.concurrent.{ExecutionContext, Future}

class MacroStatController @Inject()(macroStatService: MacroStatService, cc: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController {
  override protected def controllerComponents: ControllerComponents = cc

  def findAllMacroStats(username: String): Action[AnyContent] = Action.async { implicit request =>

    val cursor = macroStatService.findMacroStats(username)

    val futureStatsJsonArray: Future[JsArray] =
      cursor.map { stats => Json.arr(stats) }

    futureStatsJsonArray.map { stats =>
      Ok(stats)
    }
  }

  def findLastMacroStat(username: String): Action[AnyContent] = Action.async { implicit request =>

    val cursor = macroStatService.findLastMacroStat(username)

    val futureStatsJsonArray: Future[JsArray] =
      cursor.map { stats => Json.arr(stats.maxBy(_.dateTime)) }

    futureStatsJsonArray.map { stats =>
      Ok(stats)
    }
  }


}
