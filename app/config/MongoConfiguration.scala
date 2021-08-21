package config

import com.google.inject.Inject
import models.User
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, connection}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, Collection, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

class MongoConfiguration @Inject()()(implicit val ec: ExecutionContext) {

  lazy val mongoUri = "mongodb://localhost:27017"

  lazy val driver: AsyncDriver = AsyncDriver()
  lazy val parsedUri: Future[MongoConnection.ParsedURI] = MongoConnection.fromString(mongoUri)

  lazy val client: MongoClient = MongoClient(mongoUri)

  lazy val connection: Future[MongoConnection] = parsedUri.flatMap(driver.connect(_))
  lazy val db: Future[DB] = connection.flatMap(_.database("polybody"))

  lazy val userCollection: Future[BSONCollection] = db.map(_.collection[BSONCollection]("user"))
}
