package utils

import models.{MacroStat, MacroStatFull, PreviousWeight, User, UserFull}
import play.api.libs.json.Json

import java.time.LocalDate

object UserDetails {

  val noUsername = "noUsername"

  val passUsername = "Calvin"

  val macroStatRequest: MacroStatFull = {
    MacroStatFull(
      "Sedentary",
      180,
      Some(200),
      Some(100),
      Some(40),
      Some(12),
      Some("Default"),
      2500,
      3500,
      300
    )
  }

  val previousWeightList: List[PreviousWeight] = {
    List(
      PreviousWeight(LocalDate.of(2020, 6, 9), 250),
      PreviousWeight(LocalDate.of(2021, 6, 9), 180),
      PreviousWeight(LocalDate.of(2021, 7, 9), 170),
      PreviousWeight(LocalDate.of(2021, 8, 9), 165)
    )
  }

  val macroStatList: List[MacroStat] = List(
    MacroStat(
      LocalDate.of(2021, 8, 9),
      "Very Active",
      150,
      Some(150),
      Some(50),
      Some(300),
      Some(13),
      Some("Default"),
      2900,
      2500,
      95
    ),
    MacroStat(
      LocalDate.of(2021, 7, 9),
      "Active",
      140,
      Some(140),
      Some(40),
      Some(400),
      Some(15),
      Some("Default"),
      2800,
      3500,
      85
    )
  )

  val user: User = new User(
    "611be0d7e17315ce09335455",
    "Calvin",
    "calvin@gmail.com",
    LocalDate.of(1996, 10, 10),
    "male",
    140,
    Some(140),
  )

  val userFull: UserFull = new UserFull(
    "611be0d7e17315ce09335455",
    "Calvin",
    "calvin@gmail.com",
    LocalDate.of(1996, 10, 10),
    "male",
    140,
    Some(previousWeightList),
    Some(140),
    Some(macroStatList)
  )

  val json = Json.obj(
    "_id" -> "611be0d7e17315ce09335455",
    "username" -> "Calvin",
    "email" -> "calvin@gmail.com",
    "age" -> "1996-10-10",
    "gender" -> "male",
    "height" -> 140,
    "previousWeight" -> Json.arr(
      Json.obj(
        "dateTime" -> "22020-06-09",
        "weight" -> 250
      ),
      Json.obj(
        "dateTime" -> "2021-06-09",
        "weight" -> 180
      ),
      Json.obj(
        "dateTime" -> "2021-07-09",
        "weight" -> 170
      ),
      Json.obj(
        "dateTime" -> "2021-08-09",
        "weight" -> 165
      )
    ),
    "targetWeight" -> 140,
    "macroStat" -> Json.arr(
      Json.obj(
        "dateTime" -> "2021-08-09",
        "activityLevel" -> "Very Active",
        "setGoal" -> 150,
        "proteinPreference" -> 150,
        "fatPreference" -> 50,
        "carbPreference" -> 300,
        "bodyFat" -> 13,
        "maintenanceCalories" -> 2500,
        "targetCalories" -> 2000,
        "timeToGoal" -> 90
      )
    )
  )

}
