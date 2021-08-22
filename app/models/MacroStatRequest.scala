package models

import play.api.libs.json.{Json, OFormat}

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