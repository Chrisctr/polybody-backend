package config

import com.google.inject.Inject
import models.User
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, connection}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, Collection, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

class MongoConfiguration @Inject()()(implicit val ec: ExecutionContext) {

  val mongoUri = "mongodb://localhost:27017"

  val driver: AsyncDriver = AsyncDriver()
  val parsedUri: Future[MongoConnection.ParsedURI] = MongoConnection.fromString(mongoUri)

  val connection: Future[MongoConnection] = parsedUri.flatMap(driver.connect(_))
  def db: Future[DB] = connection.flatMap(_.database("polybody"))

  val userCollection = db.map(_.collection[BSONCollection]("user"))
}
