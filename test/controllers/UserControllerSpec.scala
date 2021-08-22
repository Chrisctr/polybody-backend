package controllers

import helpers.ErrorHandler
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.http.Status.{OK, NO_CONTENT}
import play.api.libs.json.{JsObject, JsValue}
import play.api.mvc.AnyContent
import play.api.mvc.Results.{NoContent, Ok}
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import services.UserService
import utils.UserDetails.{passUsername, user}
import utils.{BaseSpec, UserDetails}

import scala.concurrent.Future

class UserControllerSpec extends BaseSpec {

  private val userService = mock[UserService]

  private val errorHandler = mock[ErrorHandler]

  val sut = new UserController(
    userService,
    cc,
    errorHandler
  )

  implicit val request: FakeRequest[AnyContent] = FakeRequest()
  
  override def beforeEach(): Unit = {
    reset(
      userService,
      errorHandler
    )
  }

  "UserController" when {
    "findSpecificUser is called" must {
      "return OK response and the specific user's details in full if a valid user exists" in {

        val requestedUser = Some(Future.successful(user))

        when(userService.findSpecificUser(passUsername))
          .thenReturn(requestedUser)

        when(errorHandler.userErrorHandler(requestedUser)).thenReturn(Future.successful(Ok(any[JsValue])))

        val result = sut.findSpecificUser(passUsername)(request)

        status(result) mustBe OK
      }
      "return NO_CONTENT when no user exists" in {

        val requestedUser = None

        when(userService.findSpecificUser(passUsername))
          .thenReturn(requestedUser)

        when(errorHandler.userErrorHandler(requestedUser)).thenReturn(Future.successful(NoContent))

        val result = sut.findSpecificUser(passUsername)(request)

        status(result) mustBe NO_CONTENT
      }
    }

    //TODO - Add more when merged with error handling
  }

  //TODO - Add PUT tests when error handling is implemented
}
