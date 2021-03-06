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

class PreviousWeightService @Inject()(userConnector: UserConnector)(implicit ec: ExecutionContext) extends Logging {

  def findPreviousWeights(username: String): Option[Future[List[PreviousWeight]]] = {

    val verify = userConnector.checkUserExists(username) map {
      case true =>
        Some(
          userConnector
          .findSpecificUserFull(username)
          .map(
            _.head.previousWeight.get)
        )
      case false => None
    }
    Await.result(verify, Duration(10, "seconds"))
  }

  def addNewWeight(username: String, weight: Double): Option[Future[Int]] = {

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

    val verify = userConnector.checkUserExists(username) map {
      case true =>
        Some(
          userConnector.addElement(selector, modifier)
        )
      case false => None
    }
    Await.result(verify, Duration(10, "seconds"))
  }
}
