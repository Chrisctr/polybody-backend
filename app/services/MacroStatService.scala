package services

import com.google.inject.Inject
import connectors.UserConnector
import models.{MacroStat, MacroStatRequest, PreviousWeight}
import play.api.Logging
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class MacroStatService @Inject()(userConnector: UserConnector, userService: UserService)(implicit ec: ExecutionContext) extends Logging {

  def findMacroStats(username: String): Option[Future[List[MacroStat]]] = {

    val verify = userConnector.checkUserExists(username) map {
      case true =>
        Some(
          userConnector
            .findSpecificUser(username)
            .map(_.head.macroStat.get)
        )
      case false => None
    }
    Await.result(verify, Duration(10, "seconds"))
  }

  def findLastMacroStat(username: String): Option[Future[List[MacroStat]]] = {

    val verify = userConnector.checkUserExists(username) map {
      case true =>
        Some(
          userConnector
            .findSpecificUser(username)
            .map(_.head.macroStat.head)
        )
      case false => None
    }
    Await.result(verify, Duration(10, "seconds"))
  }

  def addNewMacroStat(
                       username: String,
                       macroStatRequest: MacroStatRequest
                     ) = {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate: String = LocalDate.now.format(dateTimeFormatter)

    //TODO Implement check
    //    userService.checkUserExists(username) match {
    //      case UserExistsAndValid =>
    val selector: BSONDocument = BSONDocument("username" -> username)
    val modifier: BSONDocument = BSONDocument(
      "$addToSet" -> BSONDocument(
        "macroStat" -> BSONDocument(
          "dateTime" -> currentDate,
          "activityLevel" -> macroStatRequest.activityLevel,
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

    userConnector.addElement(selector, modifier)

    //      case UserDoesNotExist => logger.error("No such user")

  }

}
