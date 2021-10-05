package config

import com.google.inject.Inject
import models.User
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, connection}
import play.api.Configuration
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, Collection, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

class ApplicationConfig @Inject()(val configuration: Configuration)(implicit val ec: ExecutionContext) {

  lazy val mongoUri = configuration.getOptional[String]("mongo.local.uri").getOrElse("")

  lazy val driver: AsyncDriver = AsyncDriver()
  lazy val parsedUri: Future[MongoConnection.ParsedURI] = MongoConnection.fromString(mongoUri)

  lazy val client: MongoClient = MongoClient(mongoUri)

  lazy val connection: Future[MongoConnection] = parsedUri.flatMap(driver.connect(_))
  lazy val db: Future[DB] = connection.flatMap(
    _.database(
      configuration.getOptional[String]("mongo.database").getOrElse("")
    )
  )

  lazy val userCollection = db.map(
    _.collection[BSONCollection](
      configuration.getOptional[String]("mongo.user").getOrElse("")
    )
  )
}
