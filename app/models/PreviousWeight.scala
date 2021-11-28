package models

import helpers.{DateTimeFormat, FormBinding}
import org.joda.time.DateTime
import play.api.data.Forms.{date, mapping, number, of, text, tuple}
import play.api.data.{Form, Forms, Mapping}

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import play.api.libs.json
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue, Json, OFormat}

import java.time.LocalDate

case class PreviousWeight(dateTime: LocalDate, weight: Double)

object PreviousWeight {

  implicit val dateTimeFormat: Format[DateTime] = DateTimeFormat.dateTimeFormat

  implicit val formats: OFormat[PreviousWeight] = Json.format[PreviousWeight]
}