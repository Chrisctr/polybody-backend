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
import services.{MacroStatService, PreviousWeightService}
import utils.UserDetails.{macroStatList, passUsername, previousWeightList}
import utils.{BaseSpec, UserDetails}

import scala.concurrent.Future

class MacroStatControllerSpec extends BaseSpec {

  private val macroStatService = mock[MacroStatService]

  private val errorHandler = mock[ErrorHandler]

  val sut = new MacroStatController(
    macroStatService,
    cc,
    errorHandler
  )

  implicit val request: FakeRequest[AnyContent] = FakeRequest()

  override def beforeEach(): Unit = {
    reset(
      macroStatService,
      errorHandler
    )
  }

  "PreviousWeightController" when {
    "findAllPreviousWeights is called" must {
      "return OK response and all of the user's previous weights if the user exists" in {

        val requestedUserWeights = Some(Future.successful(macroStatList))

        when(macroStatService.findMacroStats(passUsername))
          .thenReturn(requestedUserWeights)

        when(errorHandler.macroStatErrorHandler(requestedUserWeights)).thenReturn(Future.successful(Ok(any[JsValue])))

        val result = sut.findAllMacroStats(passUsername)(request)

        status(result) mustBe OK
      }
      "return NO_CONTENT when no user exists" in {

        val requestedUser = None

        when(macroStatService.findMacroStats(passUsername))
          .thenReturn(requestedUser)

        when(errorHandler.macroStatErrorHandler(requestedUser)).thenReturn(Future.successful(NoContent))

        val result = sut.findAllMacroStats(passUsername)(request)

        status(result) mustBe NO_CONTENT
      }
    }
    "findLastPreviousWeight is called" must {
      "return OK response and the last of the user's previous weights if the user exists" in {

        val requestedUserWeights = Some(Future.successful(macroStatList))

        when(macroStatService.findLastMacroStat(passUsername))
          .thenReturn(requestedUserWeights)

        when(errorHandler.macroStatErrorHandler(requestedUserWeights)).thenReturn(Future.successful(Ok(any[JsValue])))

        val result = sut.findLastMacroStat(passUsername)(request)

        status(result) mustBe OK
      }
      "return NO_CONTENT when no user exists" in {

        val requestedUser = None

        when(macroStatService.findLastMacroStat(passUsername))
          .thenReturn(requestedUser)

        when(errorHandler.macroStatErrorHandler(requestedUser)).thenReturn(Future.successful(NoContent))

        val result = sut.findLastMacroStat(passUsername)(request)

        status(result) mustBe NO_CONTENT
      }
    }

    //TODO - Add more when merged with error handling
  }



}
