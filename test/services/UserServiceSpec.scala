package services

import connectors.UserConnector
import org.mockito.Mockito.{reset, when}
import utils.UserDetails.{passUsername, user}
import utils.{BaseSpec, UserDetails}

import scala.concurrent.Future

class UserServiceSpec extends BaseSpec {

  lazy val userConnector: UserConnector = mock[UserConnector]

  val sut = new UserService(userConnector)

  override def beforeEach(): Unit = {
    reset(userConnector)
  }

  "UserService" when {
    "findSpecificUser is called" must {
      "return a valid user when a valid user exists and is retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(true))

        when(userConnector.findSpecificUser(passUsername))
          .thenReturn(Future.successful(List(user)))

        //TODO - Figure out why this test is not passing without .toString
        sut.findSpecificUser(passUsername).futureValue.get mustBe user
      }
      "return None when no user exists to be retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(false))

        sut.findSpecificUser(passUsername).futureValue mustBe None
      }
    }
  }
}
