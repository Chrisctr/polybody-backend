package helpers

import com.google.inject.Inject
import models.{MacroStat, PreviousWeight, User}
import play.api.Logging
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.Result
import play.api.mvc.Results.{NoContent, Ok}

import scala.concurrent.{ExecutionContext, Future}

class ErrorHandler @Inject()()(implicit val ec: ExecutionContext) extends Logging {
//TODO Find a way to condense userErrorHandler, previousWeightErrorHandler and macroStatErrorHandler into one method to cut down on duplication

  def userErrorHandler(data: Option[Future[User]]): Future[Result] = {
    data match {
      case Some(value) =>
        val json: Future[JsArray] = value.map {
          data => Json.arr(data)
        }
        json.map { data =>
          logger.info(data.toString)
          Ok(data)
        }
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }

  def previousWeightErrorHandler(data: Option[Future[List[PreviousWeight]]]): Future[Result] = {
    data match {
      case Some(value) =>
        val json = value.map {
          data => Json.arr(data)
        }
        json.map { data =>
          logger.info(data.toString)
          Ok(data)
        }
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }

  def macroStatErrorHandler(data: Option[Future[List[MacroStat]]]): Future[Result] = {
    data match {
      case Some(value) =>
        val json = value.map {
          data => Json.arr(data)
        }
        json.map { data =>
          logger.info(data.toString)
          Ok(data)
        }
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }
}
