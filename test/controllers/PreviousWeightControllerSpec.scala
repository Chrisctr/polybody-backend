package controllers

import helpers.ErrorHandler
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.http.Status.{BAD_REQUEST, CREATED, INTERNAL_SERVER_ERROR, NO_CONTENT, OK}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContent
import play.api.mvc.Results.{NoContent, Ok}
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import services.PreviousWeightService
import utils.BaseSpec
import utils.UserDetails.{passUsername, previousWeightList}

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

        val requestedUserWeights = Future.successful(Some(previousWeightList))

        when(previousWeightService.findPreviousWeights(passUsername))
          .thenReturn(requestedUserWeights)

        when(errorHandler.previousWeightErrorHandler(requestedUserWeights)).thenReturn(Future.successful(Ok(any[JsValue])))

        val result = sut.findAllPreviousWeights(passUsername)(request)

        status(result) mustBe OK
      }
      "return NO_CONTENT when no user exists" in {

        val requestedUser = Future.successful(None)

        when(previousWeightService.findPreviousWeights(passUsername))
          .thenReturn(requestedUser)

        when(errorHandler.previousWeightErrorHandler(requestedUser)).thenReturn(Future.successful(NoContent))

        val result = sut.findAllPreviousWeights(passUsername)(request)

        status(result) mustBe NO_CONTENT
      }
    }
  }
  "addPreviousWeight is called" must {

    val fakeJson: JsValue =
      Json.obj(
        "weight" -> 100
      )

    implicit val requestSuccess: FakeRequest[AnyContent] = FakeRequest().withJsonBody(fakeJson)

    "return OK response and add new previous weight to user data" in {

      when(previousWeightService.addNewWeight(any(), any()))
        .thenReturn(Future.successful(Some(CREATED)))

      val result = sut.addNewWeight(passUsername)(requestSuccess)

      status(result) mustBe CREATED

      // TODO - Find a way to test for the presence of new data
    }
//    "return NO_CONTENT when no user exists" in {
//
//      when(previousWeightService.addNewWeight(any(), any()))
//        .thenReturn(Future.successful(None))
//
//      val result = sut.addNewWeight(passUsername)(requestSuccess)
//
//      status(result) mustBe INTERNAL_SERVER_ERROR
//    } // TODO - Uncomment when refactoring connector
    "return BAD_REQUEST when missing request parameter" in {

      when(previousWeightService.addNewWeight(any(), any()))
        .thenReturn(Future.successful(Some(OK)))

      val result = sut.addNewWeight(passUsername)(request)

      status(result) mustBe BAD_REQUEST
    }
  }
}
