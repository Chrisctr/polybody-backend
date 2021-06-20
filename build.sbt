import play.core.PlayVersion
import play.core.PlayVersion.current

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """polybody-backend""",
    organization := "com.example",
    version := "0.1.0",
    scalaVersion := "2.13.5",
    libraryDependencies ++= AppDependencies.all,
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )



