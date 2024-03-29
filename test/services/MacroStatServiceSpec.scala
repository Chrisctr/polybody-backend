package services

import connectors.UserConnector
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.http.Status.CREATED
import utils.BaseSpec
import utils.UserDetails.{macroStatList, macroStatRequest, passUsername, previousWeightList, user, userFull}

import scala.concurrent.Future

class MacroStatServiceSpec extends BaseSpec {

  lazy val userConnector: UserConnector = mock[UserConnector]

  val sut = new MacroStatService(userConnector)

  override def beforeEach(): Unit = {
    reset(userConnector)
  }

  "MacroStatService" when {
    "findSpecificUser is called" must {
      "return a valid user when a valid user exists and is retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(true))

        when(userConnector.findSpecificUserFull(passUsername))
          .thenReturn(Future.successful(List(userFull)))

        //TODO - Figure out why this test is not passing without .toString
        sut.findMacroStats(passUsername).futureValue.get mustBe macroStatList
      }
      "return None when no user exists to be retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(false))

        sut.findMacroStats(passUsername).futureValue mustBe None
      }
    }
    "addNewMacroStat is called" must {
      "return a Some(1) when the user exists" in {

        when(userConnector.checkUserExists(any()))
          .thenReturn(Future.successful(true))

        when(userConnector.addElement(any(), any()))
          .thenReturn(Future.successful(Some(CREATED)))

        // TODO - Find a way to pass without using toString
        sut.addNewMacroStat(passUsername, macroStatRequest).futureValue.get mustBe CREATED
      }
      "return a None when the user doesn't exist" in {

        when(userConnector.checkUserExists(any()))
          .thenReturn(Future.successful(false))

        sut.addNewMacroStat(passUsername, macroStatRequest).futureValue mustBe None
      }
    }
  }
}
