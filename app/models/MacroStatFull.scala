package models

import helpers.{ActivityLevel, LightlyActive, ModeratelyActive, Sedentary, VeryActive}
import play.api.libs.json
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue, Json, OFormat}

import java.time.format.DateTimeParseException

case class MacroStatFull(
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

object MacroStatFull {

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

  implicit val formats: OFormat[MacroStatFull] = Json.format[MacroStatFull]
}
