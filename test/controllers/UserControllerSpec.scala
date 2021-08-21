package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import connectors.{UserConnector, UserConnectorSpec}
import helpers.ErrorHandler
import org.mockito.Mockito.when
import play.api.http.Status.OK
import play.api.libs.json.JsObject
import play.api.mvc.AnyContent
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import services.UserService
import utils.UserDetails.user
import utils.{BaseSpec, UserDetails}

import scala.concurrent.Future

class UserControllerSpec extends BaseSpec {

  private val userConnector = mock[UserConnector]

  private val userService = mock[UserService]

  private val errorHandler = mock[ErrorHandler]

  val sut = new UserController(
    userService,
    cc,
    errorHandler
  )

  implicit val request: FakeRequest[AnyContent] = FakeRequest()

  val json: JsObject = UserDetails.json

  "UserController" when {
    "findSpecificUser is called" must {
      "return the specific user's details in full if a valid user exists" in {



        when(userService.findSpecificUser("Calvin"))
          .thenReturn(Some(Future.successful(user)))

        val result = sut.findSpecificUser("Calvin")(request)

        status(result) mustBe OK
      }
    }

    //TODO - Add more when merged with error handling
  }

}
