package services

import connectors.UserConnector
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import play.api.http.Status.CREATED
import utils.BaseSpec
import utils.UserDetails.{passUsername, previousWeightList, user, userFull}

import scala.concurrent.Future

class PreviousWeightServiceSpec extends BaseSpec {

  lazy val userConnector: UserConnector = mock[UserConnector]

  val sut = new PreviousWeightService(userConnector)

  override def beforeEach(): Unit = {
    reset(userConnector)
  }

  "PreviousWeightService" when {
    "findPreviousWeights is called" must {
      "return a list of previous weights when a valid user exists and is retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(true))

        when(userConnector.findSpecificUserFull(passUsername))
          .thenReturn(Future.successful(List(userFull)))

        //TODO - Figure out why this test is not passing without .toString
        sut.findPreviousWeights(passUsername).futureValue.get mustBe previousWeightList
      }
      "return None when no user exists to be retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(false))

        sut.findPreviousWeights(passUsername).futureValue mustBe None
      }
    }
    "addNewWeight is called" must {
      "return a Some(1) when the user exists" in {

        when(userConnector.checkUserExists(any()))
          .thenReturn(Future.successful(true))

        when(userConnector.addElement(any(), any()))
          .thenReturn(Future.successful(Some(CREATED)))

        // TODO - Find a way to pass without using toString
        sut.addNewWeight(passUsername, 100).futureValue.get mustBe CREATED
      }
      "return a None when the user doesn't exist" in {

        when(userConnector.checkUserExists(any()))
          .thenReturn(Future.successful(false))

        sut.addNewWeight(passUsername, 100).futureValue mustBe None
      }
    }
  }
}
