package models

import org.joda.time.DateTime
import play.api.libs.json
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue, Json, OFormat}

import java.time.format.DateTimeParseException

case class MacroStatRequest(
                      activityLevel: String,
                      setGoal: Double,
                      proteinPreference: Option[Int],
                      fatPreference: Option[Int],
                      carbPreference: Option[Int],
                      bodyFat: Option[Double],
                      equationPreference: Option[String],
                      maintenanceCalories: Int,
                      targetCalories: Int,
                      timeToGoal: Int
                    )

object MacroStatRequest {

  implicit val formats: OFormat[MacroStatRequest] = Json.format[MacroStatRequest]

}