import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt._
import play.core.PlayVersion.current

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    guice,
    ws,
    "org.typelevel"       %% "cats-core"        % "2.0.0",
    "com.typesafe.play"   %% "play-json-joda"   % "2.9.2",
    "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
    "org.reactivemongo" %% "reactivemongo" % "1.0.4",
    "org.reactivemongo" %% "play2-reactivemongo" % "1.0.4-play28",
    "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.4-play28"
  )

  val test: Seq[ModuleID] = Seq(
    "org.scalatest"           %% "scalatest"                % "3.2.3",
    "com.typesafe.play"       %% "play-test"                % current,
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "5.1.0",
    "org.scalatestplus"       %% "mockito-3-4"              % "3.2.3.0",
    "org.mockito"            %  "mockito-core"        % "2.28.2",
    "org.scalacheck"         %% "scalacheck"         % "1.14.3",
    "com.github.tomakehurst" %  "wiremock-jre8"      % "2.26.1"

  ).map(_ % "test")

  val all: Seq[ModuleID] = compile ++ test
}