package connectors

import com.google.inject.Inject
import config.MongoConfiguration
import models.{MacroStat, PreviousWeight, User}
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONObjectID, document}
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}

import java.time.LocalDate
import scala.concurrent.{ExecutionContext, Future}

class UserConnector @Inject()(mongoConfiguration: MongoConfiguration)(implicit val ec: ExecutionContext) {

  def findAllUsers(username: String): Future[List[User]] =
    mongoConfiguration.userCollection.flatMap(_.find(document("username" -> username)). // query builder
      cursor[User](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[User]]()))

//  def dummyData = {
//    val previousWeightData1 = PreviousWeight(LocalDate.of(2021, 7, 1), 155)
//
//    val previousWeightData2 = PreviousWeight(LocalDate.of(2021, 8, 1), 150)
//
//    val macroStatData = MacroStat(
//      LocalDate.of(2021, 8, 1),
//      "Active",
//      140,
//      Some(150),
//      Some(70),
//      Some(300),
//      Some(15),
//      Some("Default"),
//      2500,
//      2000,
//      50
//    )
//
//    User(
//      BSONObjectID.generate().stringify,
//      "test",
//      "test@gmail.com",
//      "password",
//      21,
//      "male",
//      1.5,
//      Some(List(previousWeightData1, previousWeightData2)),
//      140,
//      Some(List(macroStatData))
//    )
//  }
//
//  def createTestUser(user: User) = {
//
//    mongoConfiguration.userCollection.flatMap(_.insert.one(user))
//
//
//  }


}
