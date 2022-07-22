package models

import helpers.{ActivityLevel, DateTimeFormat, LightlyActive, ModeratelyActive, Sedentary, VeryActive}
import org.joda.time.DateTime
import play.api.libs.json
import play.api.libs.json._

import java.time.LocalDate
import java.time.format.DateTimeParseException

case class MacroStat(
                      dateTime: LocalDate,
                      activityLevel: ActivityLevel,
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

  implicit val activityLevelFormat: Format[ActivityLevel] =
    new Format[ActivityLevel] {
      override def writes(o: ActivityLevel): JsValue =
        json.JsString(o.toString)

      override def reads(json: JsValue): JsResult[ActivityLevel] =
        json match {
          case JsString("Sedentary")        => JsSuccess(Sedentary)
          case JsString("LightlyActive")    => JsSuccess(LightlyActive)
          case JsString("ModeratelyActive") => JsSuccess(ModeratelyActive)
          case JsString("VeryActive")       => JsSuccess(VeryActive)
          case _ => JsError("That's not an activity level")
        }
    }

  implicit val formats: OFormat[MacroStat] = Json.format[MacroStat]
}