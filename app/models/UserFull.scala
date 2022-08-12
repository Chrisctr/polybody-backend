package models

import helpers.{DateTimeFormat, Female, Male, MaleOrFemale, Intersex}
import org.joda.time.DateTime
import play.api.libs.json
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue, Json, OFormat}

import java.time.LocalDate
import java.time.format.DateTimeParseException

case class UserFull(
                     _id: String,
                     username: String,
                     email: String,
                     dob: LocalDate,
                     sex: MaleOrFemale,
                     height: Double,
                     previousWeight: Option[List[PreviousWeight]],
                     targetWeight: Option[Double],
                     macroStat: Option[List[MacroStat]]
               )

object UserFull {

  implicit val dateTimeFormat: Format[DateTime] = DateTimeFormat.dateTimeFormat

  implicit val sexFormat: Format[MaleOrFemale] =
    new Format[MaleOrFemale] {
      override def writes(o: MaleOrFemale): JsValue =
        json.JsString(o.toString)

      override def reads(json: JsValue): JsResult[MaleOrFemale] =
        json match {
          case JsString("Male")   => JsSuccess(Male)
          case JsString("Female") => JsSuccess(Female)
          case JsString("Intersex")  => JsSuccess(Intersex)
          case _ => JsError("That's not a sex")
        }
    }

  implicit val formats: OFormat[UserFull] = Json.format[UserFull]

}

