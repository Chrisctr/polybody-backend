package utils

import models.{MacroStat, PreviousWeight, User}
import play.api.libs.json.Json

import java.time.LocalDate

object UserDetails {

  val previousWeightList: List[PreviousWeight] = {
    List(
      PreviousWeight(LocalDate.of(2020, 6, 9), 250),
      PreviousWeight(LocalDate.of(2021, 6, 9), 180),
      PreviousWeight(LocalDate.of(2021, 7, 9), 170),
      PreviousWeight(LocalDate.of(2021, 8, 9), 165)
    )
  }

  val macroStat: MacroStat = new MacroStat(
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
    90
  )

  val user: User = new User(
    "611be0d7e17315ce09335455",
    "Calvin",
    "calvin@gmail.com",
    "Sasquatch12!",
    25,
    "male",
    140,
    Some(previousWeightList),
    Some(140),
    Some(List(macroStat))
  )

  val json = Json.obj(
    "_id" -> "611be0d7e17315ce09335455",
    "username" -> "Calvin",
    "email" -> "calvin@gmail.com",
    "password" -> "Sasquatch12!",
    "age" -> 25,
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
