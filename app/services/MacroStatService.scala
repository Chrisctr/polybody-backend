package services

import com.google.inject.Inject
import connectors.UserConnector
import models.{MacroStat, MacroStatFull, PreviousWeight}
import play.api.Logging
import play.api.http.Status.CREATED
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class MacroStatService @Inject()(userConnector: UserConnector)(implicit ec: ExecutionContext) extends Logging {

  def findMacroStats(username: String): Future[Option[List[MacroStat]]] = {

    userConnector.checkUserExists(username) flatMap {
      case true =>
        userConnector
          .findSpecificUserFull(username)
          .map(_.head.macroStat)
      case false => Future.successful(None)
    }
  }

  def addNewMacroStat(
                       username: String,
                       macroStatRequest: MacroStatFull
                     ): Future[Option[Int]] = {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate: String = LocalDate.now.format(dateTimeFormatter)

    val selector: BSONDocument = BSONDocument("username" -> username)
    val modifier: BSONDocument = BSONDocument(
      "$addToSet" -> BSONDocument(
        "macroStat" -> BSONDocument(
          "dateTime" -> currentDate,
          "activityLevel" -> macroStatRequest.activityLevel.toString,
          "setGoal" -> macroStatRequest.setGoal,
          "proteinPreference" -> macroStatRequest.proteinPreference,
          "fatPreference" -> macroStatRequest.fatPreference,
          "carbPreference" -> macroStatRequest.carbPreference,
          "bodyFat" -> macroStatRequest.bodyFat,
          "equationPreference" -> macroStatRequest.equationPreference,
          "maintenanceCalories" -> macroStatRequest.maintenanceCalories,
          "targetCalories" -> macroStatRequest.targetCalories,
          "timeToGoal" -> macroStatRequest.timeToGoal
        )
      )
    )

    userConnector.checkUserExists(username) flatMap {
      case true =>
        userConnector.addElement(selector, modifier)
      case false => Future.successful(None)
    }
  }
}
