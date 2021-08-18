package services

import com.google.inject.Inject
import connectors.UserConnector
import models.PreviousWeight
import play.api.Logging
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.{ExecutionContext, Future}

class PreviousWeightService @Inject()(userConnector: UserConnector, userService: UserService)(implicit ec: ExecutionContext) extends Logging {

  def findPreviousWeights(username: String): Future[Option[List[PreviousWeight]]] = {

    userConnector.
      findSpecificUser(username)
      .map(_.head.previousWeight)
  }

  def findLastWeight(username: String): Future[List[PreviousWeight]] = {

    userConnector.
      findSpecificUser(username)
      .map(_.head.previousWeight.head)
  }

  def addNewWeight(username: String, weight: Double) = {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate: String = LocalDate.now.format(dateTimeFormatter)

//    userService.checkUserExists(username) match {
//      case UserExistsAndValid =>
        val selector: BSONDocument = BSONDocument("username" -> username)
        val modifier: BSONDocument = BSONDocument(
          "$addToSet" -> BSONDocument(
            "previousWeight" -> BSONDocument(
              "dateTime" -> currentDate,
              "weight" -> 350
            )
          )
        )


        userConnector.addWeight(selector, modifier)

//      case UserDoesNotExist => logger.error("No such user")

    }





}
