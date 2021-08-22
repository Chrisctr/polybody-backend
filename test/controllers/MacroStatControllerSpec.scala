package controllers

import helpers.ErrorHandler
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.Play.materializer
import play.api.http.Status.{BAD_REQUEST, NO_CONTENT, OK}
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.mvc.{AnyContent, BodyParser}
import play.api.mvc.Results.{NoContent, Ok}
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import services.MacroStatService
import utils.BaseSpec
import utils.UserDetails.{json, macroStatList, macroStatRequest, passUsername}

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
      "return OK response and add new macro stat to user data" in {

        val fakeJson: AnyContent =
          AnyContent(
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
          )

        implicit val request: FakeRequest[AnyContent] = FakeRequest().withBody(fakeJson)


        when(macroStatService.addNewMacroStat(passUsername, macroStatRequest))
          .thenReturn(Some(Future.successful(OK)))

        val result = sut.addNewMacroStat(passUsername)(request)

        status(result) mustBe OK

//        val confirm = sut.findAllMacroStats(passUsername)(request)
//
//        status(confirm) mustBe OK
      }
      "return NO_CONTENT when no user exists" in {

        when(macroStatService.addNewMacroStat(passUsername, macroStatRequest))
          .thenReturn(None)

        val result = sut.addNewMacroStat(passUsername)(request)

        status(result) mustBe NO_CONTENT

      }
      "return BAD_REQUEST when missing request parameters" in {

        when(macroStatService.addNewMacroStat(passUsername, macroStatRequest))
          .thenReturn(Some(Future.successful(OK)))

        val result = sut.addNewMacroStat(passUsername)(request)

        status(result) mustBe BAD_REQUEST
      }
    }
  }
}
