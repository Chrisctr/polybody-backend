package helpers

import com.google.inject.Inject
import models.{MacroStat, PreviousWeight, User, UserFull}
import play.api.Logging
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.Result
import play.api.mvc.Results.{NoContent, Ok}

import scala.concurrent.{ExecutionContext, Future}

class ErrorHandler @Inject()()(implicit val ec: ExecutionContext) extends Logging {
//TODO Find a way to condense userErrorHandler, previousWeightErrorHandler and macroStatErrorHandler into one method to cut down on duplication

  def userErrorHandler(data: Future[Option[User]]): Future[Result] = {
    data.flatMap {
      case Some(value) =>
        Future.successful(Ok(Json.arr(value)))
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }

  def previousWeightErrorHandler(data: Future[Option[List[PreviousWeight]]]): Future[Result] = {
    data.flatMap {
      case Some(value) =>
        Future.successful(Ok(Json.arr(value)))
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }

  def macroStatErrorHandler(data: Future[Option[List[MacroStat]]]): Future[Result] = {
    data.flatMap {
      case Some(value) =>
        Future.successful(Ok(Json.arr(value)))
      case None =>
        logger.error("NoContent")
        Future.successful(NoContent)
    }
  }
}
