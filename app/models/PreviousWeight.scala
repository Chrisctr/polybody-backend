package models

import helpers.FormBinding
import play.api.data.Forms.{date, mapping, number, of, text, tuple}
import play.api.data.{Form, Forms, Mapping}

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import play.api.libs.json
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue, Json, OFormat}

import java.time.LocalDate

case class PreviousWeight(dateTime: String, weight: Double)

object PreviousWeight {

  implicit val formats: OFormat[PreviousWeight] = Json.format[PreviousWeight]
}