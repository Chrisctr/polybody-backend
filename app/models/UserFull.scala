package models

import helpers.DateTimeFormat
import org.joda.time.DateTime
import play.api.libs.json.{Format, Json, OFormat}

import java.time.LocalDate

case class UserFull(
                     _id: String,
                     username: String,
                     email: String,
                     dob: LocalDate,
                     gender: String,
                     height: Double,
                     previousWeight: Option[List[PreviousWeight]],
                     targetWeight: Option[Double],
                     macroStat: Option[List[MacroStat]]
               )

object UserFull {

  implicit val dateTimeFormat: Format[DateTime] = DateTimeFormat.dateTimeFormat

  implicit val formats: OFormat[UserFull] = Json.format[UserFull]

}

