package services

import com.google.inject.Inject
import connectors.UserConnector
import models.{User, UserFull}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class UserService @Inject()(userConnector: UserConnector)(implicit val ec: ExecutionContext) {

  def findSpecificUser(username: String): Future[Option[User]] = {

    userConnector.checkUserExists(username) flatMap {
      case true => userConnector.findSpecificUser(username).map {
        case result if result.length == 1 => Some(result.head)
        case result if result.length > 1 => None // TODO - Add error case for multiple users with the same name and evaluate if needed
        case _ => None
      }
      case false => Future.successful(None)
    }
  }
}
