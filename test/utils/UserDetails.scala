package utils

import models.{MacroStat, PreviousWeight, User}
import play.api.libs.json.Json

import java.time.LocalDate

object UserDetails {

  val previousWeightList: List[PreviousWeight] = {
    List(
      PreviousWeight(LocalDate.of(2020, 3, 24), 150.5),
      PreviousWeight(LocalDate.of(2020, 2, 24), 144.5)
    )
  }

  val macroStat: MacroStat = new MacroStat(
    LocalDate.of(2020, 3, 24),
    "Very Active",
    160,
    Some(150),
    Some(50),
    Some(200),
    Some(20),
    None,
    2500,
    2000,
    10
  )

  val user: User = new User(
    "611be0d7e17315ce09335455",
    "Calvin",
    "calvin@gmail.com",
    "testPassword",
    25,
    "male",
    175.5,
    Some(previousWeightList),
    Some(165),
    Some(List(macroStat))
  )

  val json = Json.obj(
    "_id" -> "611be0d7e17315ce09335455",
    "username" -> "Calvin",
    "email" -> "calvin@gmail.com",
    "password" -> "testPassword",
    "age" -> 25,
    "gender" -> "male",
    "height" -> 175.5,
    "previousWeight" -> Json.arr(
      Json.obj(
        "dateTime" -> "2020-03-24",
        "weight" -> 150.5
      ),
      Json.obj(
        "dateTime" -> "2020-02-24",
        "weight" -> 144.5
      )
    ),
    "targetWeight" -> 165,
    "macroStat" -> Json.arr(
      Json.obj(
        "dateTime" -> "2020-03-24",
        "activityLevel" -> "Very Active",
        "setGoal" -> 160,
        "proteinPreference" -> 150,
        "fatPreference" -> 50,
        "carbPreference" -> 200,
        "bodyFat" -> 20,
        "maintenanceCalories" -> 2500,
        "targetCalories" -> 2000,
        "timeToGoal" -> 10
      )
    )
  )

}
