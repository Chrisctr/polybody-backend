package controllers

import connectors.UserConnector
import helpers.ErrorHandler
import models.MacroStatRequest
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.http.Status.{BAD_REQUEST, CREATED, NO_CONTENT, OK}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContent
import play.api.mvc.Results.{NoContent, Ok}
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import services.MacroStatService
import utils.BaseSpec
import utils.UserDetails.{macroStatList, macroStatRequest, passUsername}

import scala.concurrent.Future

class MacroStatControllerSpec extends BaseSpec {

  private val userConnector = mock[UserConnector]

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

  "MacroStatController" when {
    "findAllMacroStats is called" must {
      "return OK response and all of the user's macro stats if the user exists" in {

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
    "addNewMacroStat is called" must {

      val fakeJson: JsValue =
        Json.obj(
          "activityLevel" -> "Active",
          "setGoal" -> 200,
          "proteinPreference" -> 155,
          "fatPreference" -> 55,
          "carbPreference" -> 400,
          "bodyFat" -> 11,
          "equationPreference" -> "Default",
          "maintenanceCalories" -> 2800,
          "targetCalories" -> 3500,
          "timeToGoal" -> 160
        )

      implicit val requestSuccess: FakeRequest[AnyContent] = FakeRequest().withJsonBody(fakeJson)

      "return OK response and add new macro stat to user data" in {

        when(macroStatService.addNewMacroStat(any(), any()))
          .thenReturn(Some(Future.successful(OK)))

        val result = sut.addNewMacroStat(passUsername)(requestSuccess)

        status(result) mustBe CREATED

      // TODO - Find a way to test for the presence of new data
      }
      "return NO_CONTENT when no user exists" in {

        when(macroStatService.addNewMacroStat(any(), any()))
          .thenReturn(None)

        val result = sut.addNewMacroStat(passUsername)(requestSuccess)

        status(result) mustBe NO_CONTENT
      }
      "return BAD_REQUEST when missing request parameters" in {

        when(macroStatService.addNewMacroStat(any(), any()))
          .thenReturn(Some(Future.successful(OK)))

        val result = sut.addNewMacroStat(passUsername)(request)

        status(result) mustBe BAD_REQUEST
      }
    }
  }
}
