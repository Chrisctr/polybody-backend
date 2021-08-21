package connectors

import com.google.inject.Inject
import config.MongoConfiguration
import models.User
import play.api.Logging
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, document}
import reactivemongo.play.json.compat.json2bson.toDocumentReader

import scala.concurrent.{ExecutionContext, Future}

class UserConnector @Inject()(mongoConfiguration: MongoConfiguration)(implicit val ec: ExecutionContext) extends Logging {

  def checkUserExists(username: String): Future[Boolean] = {
    checkDatabaseExits()
    mongoConfiguration.userCollection.flatMap(_.count(Some(document("username" -> username)))) map {
      case 0L => false
      case _ => true
    }
  }

  ///TODO Implement scenario where connection to database fails
  def checkDatabaseExits() = {
    println(mongoConfiguration.client.getDatabase("polybody"))
  }

  def findSpecificUser(username: String): Future[List[User]] = {
    mongoConfiguration.userCollection.flatMap(_.find(document("username" -> username))
      .cursor[User]().
      collect[List](-1, Cursor.FailOnError[List[User]]()))
  }

  def addElement(selector: BSONDocument, modifier: BSONDocument): Future[Int] = {
    mongoConfiguration.userCollection.flatMap(_.update.one(selector, modifier, upsert = true)).map(_.n)
  }
}
