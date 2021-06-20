package service

import com.google.inject.Inject
import config.MongoConfiguration
import models.User
import play.api.libs.json.{JsObject, Json}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.bson.document
import reactivemongo.play.json.compat.json2bson.{toDocumentReader, toDocumentWriter}

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(mongoConfiguration: MongoConfiguration)(implicit val ec: ExecutionContext) {

  //: Future[List[User]]

//  def findAllUsers: Future[List[User]] = {
//    val cursor: Future[Cursor[JsObject]] = mongoConfiguration.userCollection.map {
//      _.find(Json.obj()).sort(Json.obj("created" -> 1)).cursor[JsObject](ReadPreference.primary)
//    }
//
//    val futureUsersList: Future[List[JsObject]] =
//      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))
//
//    val futureCustomersJsonArray: Future[JsArray] = futureUsersList.map { user => Json.arr(user) }
//
//    futureCustomersJsonArray.map { customers =>
//      customers.value
//    }
//  }

  def findAllUsers(username: String): Future[List[User]] =
    mongoConfiguration.userCollection.flatMap(_.find(document("username" -> username)). // query builder
      cursor[User](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[User]]()))


}
