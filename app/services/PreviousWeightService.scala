package services

import com.google.inject.Inject
import connectors.UserConnector
import helpers.{UserDoesNotExist, UserExistsAndValid}
import models.PreviousWeight
import play.api.Logging
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class PreviousWeightService @Inject()(userConnector: UserConnector, userService: UserService)(implicit ec: ExecutionContext) extends Logging {

  def findPreviousWeights(username: String): Option[Future[Option[List[PreviousWeight]]]] = {

    val verify = userConnector.checkUserExists(username) map {
      case true =>
        Some(
          userConnector
            .findSpecificUser(username)
            .map(_.head.previousWeight)
        )
      case false => None
    }
    Await.result(verify, Duration(10, "seconds"))
  }

  def findLastWeight(username: String): Future[List[PreviousWeight]] = {

    userConnector.
      findSpecificUser(username)
      .map(_.head.previousWeight.head)
  }

  def addNewWeight(username: String, weight: Double): Future[Int] = {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate: String = LocalDate.now.format(dateTimeFormatter)

    //TODO Implement check
//    userConnector.checkUserExists(username) match {
//      case UserExistsAndValid =>
        val selector: BSONDocument = BSONDocument("username" -> username)
        val modifier: BSONDocument = BSONDocument(
          "$addToSet" -> BSONDocument(
            "previousWeight" -> BSONDocument(
              "dateTime" -> currentDate,
              "weight" -> weight
            )
          )
        )
        userConnector.addElement(selector, modifier)

//      case UserDoesNotExist =>
//        logger.error("No such user")
//        Future.successful(400)
//    }
  }
}
