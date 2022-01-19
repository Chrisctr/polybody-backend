package models

import helpers.DateTimeFormat
import org.joda.time.DateTime
import play.api.libs.json.{Format, Json, OFormat}

import java.time.LocalDate

case class User(
                 _id: String,
                 username: String,
                 email: String,
                 dob: LocalDate,
                 sex: String,
                 height: Double,
                 targetWeight: Option[Double],
               )

object User {

  implicit val dateTimeFormat: Format[DateTime] = DateTimeFormat.dateTimeFormat

  implicit val formats: OFormat[User] = Json.format[User]

}