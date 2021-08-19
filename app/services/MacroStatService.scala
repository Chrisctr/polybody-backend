package services

import com.google.inject.Inject
import connectors.UserConnector
import models.{MacroStat, PreviousWeight}
import play.api.Logging
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.{ExecutionContext, Future}

class MacroStatService @Inject()(userConnector: UserConnector, userService: UserService)(implicit ec: ExecutionContext) extends Logging {

  def findMacroStats(username: String): Future[Option[List[MacroStat]]] = {

    userConnector.
      findSpecificUser(username)
      .map(_.head.macroStat)
  }

  def findLastMacroStat(username: String): Future[List[MacroStat]] = {

    userConnector.
      findSpecificUser(username)
      .map(_.head.macroStat.head)
  }

//  def addNewMacroStat(username: String, weight: Double) = {
//
//    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    val currentDate: String = LocalDate.now.format(dateTimeFormatter)
//
//    //    userService.checkUserExists(username) match {
//    //      case UserExistsAndValid =>
//    val selector: BSONDocument = BSONDocument("username" -> username)
//    val modifier: BSONDocument = BSONDocument(
//      "$addToSet" -> BSONDocument(
//        "previousWeight" -> BSONDocument(
//          "dateTime" -> currentDate,
//          "weight" -> weight
//        )
//      )
//    )
//
//    userConnector.addWeight(selector, modifier)
//
//    //      case UserDoesNotExist => logger.error("No such user")
//
//  }

}
