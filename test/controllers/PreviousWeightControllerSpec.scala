package controllers

import helpers.ErrorHandler
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.http.Status.{NO_CONTENT, OK}
import play.api.libs.json.{JsObject, JsValue}
import play.api.mvc.AnyContent
import play.api.mvc.Results.{NoContent, Ok}
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import services.{PreviousWeightService, UserService}
import utils.UserDetails.{passUsername, previousWeightList, user}
import utils.{BaseSpec, UserDetails}

import scala.concurrent.Future

class PreviousWeightControllerSpec extends BaseSpec {

  private val previousWeightService = mock[PreviousWeightService]

  private val errorHandler = mock[ErrorHandler]

  val sut = new PreviousWeightController(
    previousWeightService,
    cc,
    errorHandler
  )

  implicit val request: FakeRequest[AnyContent] = FakeRequest()

  override def beforeEach(): Unit = {
    reset(
      previousWeightService,
      errorHandler
    )
  }

  "PreviousWeightController" when {
    "findAllPreviousWeights is called" must {
      "return OK response and all of the user's previous weights if the user exists" in {

        val requestedUserWeights = Some(Future.successful(previousWeightList))

        when(previousWeightService.findPreviousWeights(passUsername))
          .thenReturn(requestedUserWeights)

        when(errorHandler.previousWeightErrorHandler(requestedUserWeights)).thenReturn(Future.successful(Ok(any[JsValue])))

        val result = sut.findAllPreviousWeights(passUsername)(request)

        status(result) mustBe OK
      }
      "return NO_CONTENT when no user exists" in {

        val requestedUser = None

        when(previousWeightService.findPreviousWeights(passUsername))
          .thenReturn(requestedUser)

        when(errorHandler.previousWeightErrorHandler(requestedUser)).thenReturn(Future.successful(NoContent))

        val result = sut.findAllPreviousWeights(passUsername)(request)

        status(result) mustBe NO_CONTENT
      }
    }

    //TODO - Add more when merged with error handling
  }


  //TODO - Add PUT request tests
}
