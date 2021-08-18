package services

import cats.data.OptionT
import com.google.inject.Inject
import connectors.UserConnector
import helpers.{UserDoesNotExist, UserExistsAndValid}
import models.{PreviousWeight, User}
import play.api.Logging
import play.api.libs.json.{JsObject, Json}
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONArray, BSONDocument, document}

import java.time.LocalDate
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
    val json =
      "dateTime" -> LocalDate.now
      "weight" -> weight



//    userService.checkUserExists(username) match {
//      case UserExistsAndValid =>
        val selector: BSONDocument = BSONDocument("username" -> username)
        val modifier: BSONDocument = BSONDocument(
          "$addToSet" -> BSONDocument(
            "previousWeight" -> BSONDocument(
              "index" -> 4,
              "dateTime" -> "2021-09-09",
              "weight" -> 350
            )
          )
        )


        userConnector.addWeight(selector, modifier)

//      case UserDoesNotExist => logger.error("No such user")

    }





}
