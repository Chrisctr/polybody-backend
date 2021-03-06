package models

import helpers.{Male, VeryActive}
import play.api.libs.json.{JsResultException, Json}
import utils.BaseSpec

import java.time.LocalDate

class UserSpec extends BaseSpec {

  val previousWeightList: List[PreviousWeight] = {
    List(
      PreviousWeight(LocalDate.of(2020, 3, 24), 150.5),
      PreviousWeight(LocalDate.of(2020, 2, 24), 144.5)
    )
  }

  val macroStat: MacroStat = new MacroStat(
    LocalDate.of(2020, 3, 24),
    VeryActive,
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

  val user: UserFull = new UserFull(
    "testId",
    "testUsername",
    "testEmail@email.com",
    LocalDate.of(1996, 10, 10),
    Male,
    175.5,
    Some(previousWeightList),
    Some(165),
    Some(List(macroStat))
  )

  "User" must {

    val json = Json.obj(
      "_id" -> "testId",
      "username" -> "testUsername",
      "email" -> "testEmail@email.com",
      "dob" -> "1996-10-10",
      "sex" -> "Male",
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
          "activityLevel" -> "VeryActive",
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

    "deserialise valid values" in {

      val result = json.as[UserFull]

      result mustBe user

    }

    "deserialise invalid values" in {

      val invalidJson = Json.obj(
        "_id" -> 39,
        "username" -> 0,
        "email" -> 0,
        "dob" -> "12996-110-150",
        "sex" -> "Male",
        "height" -> "175.5",
        "previousWeight" -> Json.arr(),
        "targetWeight" -> "150.5",
        "macroStat" -> Json.arr(),
      )

      val ex = intercept[JsResultException] {
        invalidJson.as[UserFull]
      }

      ex.getMessage mustBe "JsResultException(errors:List((/dob,List(JsonValidationError(List(error.expected.date.isoformat),ArraySeq(ParseCaseSensitive(false)(Value(Year,4,10,EXCEEDS_PAD)'-'Value(MonthOfYear,2)'-'Value(DayOfMonth,2))[Offset(+HH:MM:ss,'Z')])))), (/height,List(JsonValidationError(List(error.expected.jsnumber),List()))), (/username,List(JsonValidationError(List(error.expected.jsstring),List()))), (/_id,List(JsonValidationError(List(error.expected.jsstring),List()))), (/targetWeight,List(JsonValidationError(List(error.expected.jsnumber),List()))), (/email,List(JsonValidationError(List(error.expected.jsstring),List())))))"
    }

    "deserialise invalid sex" in {

      val invalidJson = Json.obj(
        "_id" -> "testId",
        "username" -> "testUsername",
        "email" -> "testEmail@email.com",
        "dob" -> "1996-10-10",
        "sex" -> 0,
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
            "activityLevel" -> "VeryActive",
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

      val ex = intercept[JsResultException] {
        invalidJson.as[UserFull]
      }

      ex.getMessage mustBe "JsResultException(errors:List((/sex,List(JsonValidationError(List(That's not a sex),List())))))"
    }

    "deserialise invalid key" in {

      val invalidJson = Json.obj(
        "invalidKey" -> "testUsername",
        "invalidKey" -> "testEmail@email.com",
        "invalidKey" -> "1996-10-10",
        "invalidKey" -> "male",
        "invalidKey" -> 175.5,
        "invalidKey" -> Json.arr(),
        "invalidKey" -> 165,
        "invalidKey" -> Json.arr()
      )

      val ex = intercept[JsResultException] {
        invalidJson.as[UserFull]
      }

      ex.getMessage mustBe "JsResultException(errors:List((/dob,List(JsonValidationError(List(error.path.missing),List()))), (/height,List(JsonValidationError(List(error.path.missing),List()))), (/username,List(JsonValidationError(List(error.path.missing),List()))), (/_id,List(JsonValidationError(List(error.path.missing),List()))), (/email,List(JsonValidationError(List(error.path.missing),List()))), (/sex,List(JsonValidationError(List(error.path.missing),List())))))"
    }

    "serialise to json" in {

      Json.toJson(user) mustBe json
    }

    "serialise/deserialise to the same value" in {

      val result = Json.toJson(user).as[UserFull]

      result mustBe user
    }
  }
}
