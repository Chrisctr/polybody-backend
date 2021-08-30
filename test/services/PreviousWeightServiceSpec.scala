package services

import connectors.UserConnector
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, when}
import utils.BaseSpec
import utils.UserDetails.{passUsername, previousWeightList, user}

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

        when(userConnector.findSpecificUser(passUsername))
          .thenReturn(Future.successful(List(user)))

        //TODO - Figure out why this test is not passing without .toString
        sut.findPreviousWeights(passUsername).toString mustBe Some(Future.successful(previousWeightList)).toString
      }
      "return None when no user exists to be retrieved from the connector" in {

        when(userConnector.checkUserExists(passUsername))
          .thenReturn(Future.successful(false))

        sut.findPreviousWeights(passUsername) mustBe None
      }
    }
    "addNewWeight is called" must {
      "return a Some(1) when the user exists" in {

        when(userConnector.checkUserExists(any()))
          .thenReturn(Future.successful(true))

        when(userConnector.addElement(any(), any()))
          .thenReturn(Future.successful(1))

        // TODO - Find a way to pass without using toString
        sut.addNewWeight(passUsername, 100).toString mustBe Some(Future.successful(1)).toString
      }
      "return a None when the user doesn't exist" in {

        when(userConnector.checkUserExists(any()))
          .thenReturn(Future.successful(false))

        sut.addNewWeight(passUsername, 100) mustBe None
      }
    }
  }
}
