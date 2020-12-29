import complete.DefaultParsers._
import sbt.complete.Parser

name := "FHP"
resolvers += "emueller-bintray" at "http://dl.bintray.com/emueller/maven"

lazy val commonSettings = Seq(
  organization := "com.cain",
  version := "0.1",
  scalaVersion := "2.12.8"
)
scalaVersion := "2.12.11"

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "FHP",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.1.5",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3",
      "com.typesafe.akka" %% "akka-stream-kafka" % "1.0.4",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "com.typesafe.slick" %% "slick-codegen" % "3.3.0",
      "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "1.0-RC1",
      "org.postgresql" % "postgresql" % "42.2.5",
      "ch.qos.logback" % "logback-classic" % "1.2.3",

      // trade scraping:
      "org.jsoup" % "jsoup" % "1.11.3",

      // testing:
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",

      // api marshalling:
      "org.json4s" %% "json4s-jackson" % "3.6.7",
    ),
    excludeDependencies += "org.slf4j" % "slf4j-log4j12",
  )
