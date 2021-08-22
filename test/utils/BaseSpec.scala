package utils

import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.ControllerComponents
import play.api.test.Injecting

import scala.concurrent.ExecutionContext

class BaseSpec extends AnyWordSpec with GuiceOneAppPerSuite with MockitoSugar with Matchers with Injecting with BeforeAndAfterEach {

  implicit lazy val ec = inject[ExecutionContext]

  lazy val cc = inject[ControllerComponents]


}
