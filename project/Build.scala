import sbt._
import sbt.Keys._
import scoverage.ScoverageSbtPlugin

object Build extends sbt.Build {

  object Versions {
    val akka = "2.3.3"
  }

  object Dependencies {
    lazy val akka = Seq(
      "com.typesafe.akka" %% "akka-actor" % Versions.akka,
      "com.typesafe.akka" %% "akka-testkit" % Versions.akka
    )
    lazy val bitcoinj = "com.google" % "bitcoinj" % "0.11.3"
    lazy val jodaTime = "joda-time" % "joda-time" % "2.3"
    lazy val jodaConvert = "org.joda" % "joda-convert" % "1.6"
    lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.2"
    lazy val logbackCore = "ch.qos.logback" % "logback-core" % "1.1.2"
    lazy val mockito = "org.mockito" % "mockito-all" % "1.9.5"
    lazy val scalatest = "org.scalatest" %% "scalatest" % "2.1.7"
    lazy val slf4j = "org.slf4j" % "slf4j-api" % "1.7.7"
  }

  lazy val root = (Project(id = "scalabtc", base = file("."))
    aggregate(bitcoin)
    settings(ScoverageSbtPlugin.instrumentSettings: _*)
  )

  lazy val bitcoin = (Project(id = "bitcoin", base = file("bitcoin"))
    settings(ScoverageSbtPlugin.instrumentSettings: _*)
  )
}
