package models

import play.api.libs.json.{Json, OFormat}

case class UserFull(
                 _id: String,
                 username: String,
                 email: String,
                 age: Int,
                 gender: String,
                 height: Double,
                 previousWeight: Option[List[PreviousWeight]],
                 targetWeight: Option[Double],
                 macroStat: Option[List[MacroStatFull]]
               )

object UserFull {

  implicit val formats: OFormat[UserFull] = Json.format[UserFull]

}

