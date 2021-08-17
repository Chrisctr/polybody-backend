package models

import helpers.FormBinding
import play.api.data.{Form, Mapping}
import play.api.libs.json.{Json, OFormat}
import play.api.data.Forms.{date, list, mapping, number, of, optional, text, tuple}


case class User(
                 _id: String,
                 username: String,
                 email: String,
                 password: String,
                 age: Int,
                 gender: String,
                 height: Double,
                 previousWeight: Option[List[PreviousWeight]],
                 targetWeight: Option[Double],
                 macroStat: Option[List[MacroStat]]
               )

object User {

  implicit val formats: OFormat[User] = Json.format[User]

}

