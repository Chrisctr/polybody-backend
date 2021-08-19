package connectors

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, urlEqualTo}
import config.ApplicationConfig
import org.mockito.Mockito.when
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import play.api.Application
import play.api.http.Status.OK
import play.api.inject.guice.GuiceApplicationBuilder
import utils.{BaseSpec, UserDetails, WireMockHelper}

import scala.concurrent.Future

class UserConnectorSpec extends BaseSpec with WireMockHelper with ScalaFutures with IntegrationPatience {

  val config = inject[ApplicationConfig]

  lazy val userConnector = inject[UserConnector]

  override lazy val app: Application = GuiceApplicationBuilder()
    .build()

  "userConnector" when {
    "findSpecificUser is called" must {
      "return a valid User" in {

        server.stubFor(
          WireMock.get(urlEqualTo(config.mongoUri + "/polybody"))
            .willReturn(aResponse().withStatus(OK).withBody(UserDetails.json.toString))
        )

        userConnector.findSpecificUser("Calvin").futureValue mustBe List(UserDetails.user)



      }
    }
  }

}
