package services

import com.google.inject.Inject
import connectors.UserConnector
import helpers.{UserDoesNotExist, UserExistsAndValid}
import models.PreviousWeight
import play.api.Logging
import play.api.http.Status.{CREATED, NOT_FOUND}
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class PreviousWeightService @Inject()(userConnector: UserConnector)(implicit ec: ExecutionContext) extends Logging {

  def findPreviousWeights(username: String): Future[Option[List[PreviousWeight]]] = {

    userConnector.checkUserExists(username) flatMap {
      case true =>
        userConnector
          .findSpecificUserFull(username)
          .map(_.head.previousWeight)
      case false => Future.successful(None)
    }
  }

  def addNewWeight(username: String, weight: Double): Future[Option[Int]] = {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate: String = LocalDate.now.format(dateTimeFormatter)

    val selector: BSONDocument = BSONDocument("username" -> username)
    val modifier: BSONDocument = BSONDocument(
      "$addToSet" -> BSONDocument(
        "previousWeight" -> BSONDocument(
          "dateTime" -> currentDate,
          "weight" -> weight
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
