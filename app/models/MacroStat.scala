package models

import play.api.libs.json.{Json, OFormat}

case class MacroStat(
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

object MacroStat {

  implicit val formats: OFormat[MacroStat] = Json.format[MacroStat]

}