package connectors

import config.ApplicationConfig
import org.mockito.Mockito
import org.mockito.Mockito.{reset, when}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import utils.UserDetails.passUsername
import utils.{BaseSpec, UserDetails}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class UserConnectorSpec extends BaseSpec with ScalaFutures with IntegrationPatience {

  val config = inject[ApplicationConfig]

  lazy val userConnector = mock[UserConnector]

  override lazy val app: Application = GuiceApplicationBuilder()
    .build()

  override def beforeEach(): Unit = {
    reset(userConnector)
  }

  "userConnector" when {
    "findSpecificUser is called" must {
      "return a List with a single user when a valid user is available" in {

        when(userConnector.findSpecificUser(passUsername)).thenReturn(Future.successful(List(UserDetails.user)))

        val response = userConnector.findSpecificUser(passUsername)

        val result = Await.result(response, Duration(5, "seconds"))

        result mustBe List(UserDetails.user)
      }
      "return an empty List when no users are available" in {

        when(userConnector.findSpecificUser("")).thenReturn(Future.successful(List()))

        val response = userConnector.findSpecificUser("")

        val result = Await.result(response, Duration(5, "seconds"))

        result mustBe List()
      }
      //TODO - Add test for when database is down after implementing error handling
    }
  }

}
