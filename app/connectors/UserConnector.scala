package connectors

import akka.http.scaladsl.model.HttpResponse
import akka.stream.StreamRefMessages.Payload
import com.google.inject.Inject
import config.MongoConfiguration
import models.{MacroStat, PreviousWeight, User}
import play.api.libs.json.JsObject
import play.libs.Json
import reactivemongo.api.Cursor
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONObjectID, array, document}
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}

import java.time.LocalDate
import scala.concurrent.{ExecutionContext, Future}

class UserConnector @Inject()(mongoConfiguration: MongoConfiguration)(implicit val ec: ExecutionContext) {

  def findSpecificUser(username: String): Future[List[User]] = {
    mongoConfiguration.userCollection.flatMap(_.find(document("username" -> username)). // query builder
      cursor[User](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[User]]()))
  }

  def addWeight(selector: BSONDocument, modifier: BSONDocument): Future[Int] = {
    mongoConfiguration.userCollection.flatMap(_.update.one(selector, modifier, upsert = true)).map(_.n)
  }
}
