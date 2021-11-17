package services

import com.google.inject.Inject
import connectors.UserConnector
import models.{User, UserFull}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class UserService @Inject()(userConnector: UserConnector)(implicit val ec: ExecutionContext) {

  def findSpecificUser(username: String): Option[Future[User]] = {

    val verify = userConnector.checkUserExists(username) map {
      case true => Some(userConnector.findSpecificUser(username).map(_.head))
      case false => None
    }
    Await.result(verify, Duration(10, "seconds"))
  }
}
