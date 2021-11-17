package connectors

import com.google.inject.Inject
import config.ApplicationConfig
import models.{User, UserFull}
import play.api.Logging
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, document}
import reactivemongo.play.json.compat.json2bson.toDocumentReader

import scala.concurrent.{ExecutionContext, Future}

class UserConnector @Inject()(applicationConfiguration: ApplicationConfig)(implicit val ec: ExecutionContext) extends Logging {

  def checkUserExists(username: String): Future[Boolean] = {
    checkDatabaseExits()
    applicationConfiguration.userCollection.flatMap(_.count(Some(document("username" -> username)))) map {
      case 0L => false
      case _ => true
    }
  }

  ///TODO Implement scenario where connection to database fails
  def checkDatabaseExits() = {
    println(applicationConfiguration.client.getDatabase("polybody"))
  }


  def findSpecificUser(username: String): Future[List[User]] = {
    applicationConfiguration.userCollection.flatMap(_.find(document("username" -> username))
      .cursor[User]()
      .collect[List](-1, Cursor.FailOnError[List[User]]()))
  }

  def findSpecificUserFull(username: String): Future[List[UserFull]] = {
    applicationConfiguration.userCollection.flatMap(_.find(document("username" -> username))
      .cursor[UserFull]()
      .collect[List](-1, Cursor.FailOnError[List[UserFull]]()))
  }

  def addElement(selector: BSONDocument, modifier: BSONDocument): Future[Int] = {
    applicationConfiguration.userCollection.flatMap(_.update.one(selector, modifier, upsert = true)).map(_.n)
  }
}
