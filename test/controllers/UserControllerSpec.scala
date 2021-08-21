package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import connectors.{UserConnector, UserConnectorSpec}
import org.mockito.Mockito.when
import play.api.http.Status.OK
import play.api.mvc.AnyContent
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import utils.UserDetails.user
import utils.{BaseSpec, UserDetails}

import scala.concurrent.Future

class UserControllerSpec extends BaseSpec {

  private val userConnector = mock[UserConnector]

  val sut = new UserController(
    userConnector,
    cc
  )

  implicit val request: FakeRequest[AnyContent] = FakeRequest()

  val json = UserDetails.json

  "UserController" when {
    "findSpecificUser is called" must {
      "return the specific user's details in full" in {

        when(userConnector.findSpecificUser("Calvin"))
          .thenReturn(Future.successful(List(user)))

        val result = sut.findSpecificUser("Calvin")(request)

        status(result) mustBe OK
      }
    }

    //TODO - Add more when merged with error handling
  }

}
