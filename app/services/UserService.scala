package services

import com.google.inject.Inject
import connectors.UserConnector
import helpers.{UserDoesNotExist, UserExistsAndValid}
import play.api.mvc.Results.{BadRequest, NotFound, Ok}

import scala.concurrent.Future

class UserService @Inject()(userConnector: UserConnector) {

  def checkUserExists(username: String) = {
    val userSearch = Option(userConnector.findSpecificUser(username))

    userSearch match {
      case None => UserDoesNotExist
      case _ => UserExistsAndValid
    }
  }

}
