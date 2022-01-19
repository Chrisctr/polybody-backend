package models

import helpers.DateTimeFormat
import org.joda.time.DateTime
import play.api.libs.json._

import java.time.LocalDate

case class MacroStat(
                      dateTime: LocalDate,
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

  implicit val dateTimeFormat: Format[DateTime] = DateTimeFormat.dateTimeFormat

  implicit val formats: OFormat[MacroStat] = Json.format[MacroStat]

}