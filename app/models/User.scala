package models

import play.api.libs.json.{Json, OFormat}

case class User(
                 username: String,
                 email: String,
                 password: String,
                 age: Int,
                 gender: String,
                 height: Double,
                 previousWeight: List[PreviousWeight],
                 targetWeight: Double,
                 macroStat: List[MacroStat]
               )

object User {

  implicit val formats: OFormat[User] = Json.format[User]

}

