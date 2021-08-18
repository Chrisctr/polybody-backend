package controllers

import org.mockito.Mockito.{times, verify}
import play.api.http.Status.OK
import play.api.libs.json.Json
import play.api.mvc.ControllerComponents
import play.api.test.FakeRequest
import play.api.test.Helpers.{CONTENT_TYPE, contentAsJson, defaultAwaitTimeout, status}
import services.PreviousWeightService
import utils.UserDetails._
import utils._

class PreviousWeightControllerSpec extends BaseSpec {

  val mockPreviousWeightService: PreviousWeightService = mock[PreviousWeightService]
  val mockControllerComponents: ControllerComponents = mock[ControllerComponents]
  val controller = new PreviousWeightController(mockPreviousWeightService, mockControllerComponents)

  "PreviousWeightController" must {
    "return 200 with all weights" in {

      val request = FakeRequest("GET", s"/findAllPreviousWeights/${user.username}")
        .withHeaders(CONTENT_TYPE -> "application/json")

      val result = controller.findAllPreviousWeights(user.username).apply(request)
      status(result) mustBe OK
      (contentAsJson(result) \ "weights").as[String] mustBe Some(previousWeightList)

      verify(mockPreviousWeightService, times(1))

    }

    "return 200 with last weight" in {

      val request = FakeRequest("GET", s"/findLastPreviousWeights/${user.username}")
        .withHeaders(CONTENT_TYPE -> "application/json")

      val result = controller.findLastPreviousWeight(user.username).apply(request)
      status(result) mustBe OK
      (contentAsJson(result) \ "weights").as[String] mustBe Some(previousWeightList)

      verify(mockPreviousWeightService, times(1))

    }
  }

}
