package connectors

import com.google.inject.Inject
import config.MongoConfiguration
import models.User
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, document}
import reactivemongo.play.json.compat.json2bson.toDocumentReader

import scala.concurrent.{ExecutionContext, Future}

class UserConnector @Inject()(mongoConfiguration: MongoConfiguration)(implicit val ec: ExecutionContext) {

  def findSpecificUser(username: String): Future[List[User]] = {
    mongoConfiguration.userCollection.flatMap(_.find(document("username" -> username)).
      cursor[User]().
      collect[List](-1, Cursor.FailOnError[List[User]]()))
  }

  def addWeight(selector: BSONDocument, modifier: BSONDocument): Future[Int] = {
    mongoConfiguration.userCollection.flatMap(_.update.one(selector, modifier, upsert = true)).map(_.n)
  }
}
