import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object SESMSBuild extends Build {
  val Organization = "com.perevillega"
  val Version      = "0.1"
  val ScalaVersion = "2.10.2"

  lazy val mindCandySample = Project(
    id = "s-esms",
    base = file("."),
    settings = defaultSettings ++ Seq(
      libraryDependencies ++= Dependencies.sesmsDependencies
    )
  )

  lazy val buildSettings = Project.defaultSettings ++ Seq(
    organization := Organization,
    version      := Version,
    scalaVersion := ScalaVersion,
    crossPaths   := false,
    organizationName := "Pere Villega",
    organizationHomepage := Some(url("http://www.perevillega.com"))
  )
  
  lazy val defaultSettings = buildSettings ++ assemblySettings ++ Seq(
    // compile options
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked"),
    javacOptions  ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")
  )
}

object Dependencies {
  import Libraries._

  val sesmsDependencies = Seq(logback, scalaTest)
}

object Libraries {

  object Versions {
    val logback = "1.0.0"
    val scalaTest = "1.9.1"
  }

  val logback    = "ch.qos.logback" % "logback-classic" % Versions.logback
  val scalaTest  = "org.scalatest" % "scalatest_2.10" % Versions.scalaTest % "test"
}

