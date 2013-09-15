package com.perevillega.sesms

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.{JsArray, Json}

/**
 * Tests for TeamSheet
 * User: pvillega
 */
class TeamSheetTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  import PlayerType._
  import TacticType._
  import TeamSheet._

  describe("A TeamSheet object") {

    it("should load a TeamSheet from JSON") {
      val starting = List((GK, "GK"), (DF, "DF"), (DF, "DF2"), (DF, "DF3"), (DF, "DF4"), (MF, "MF"), (MF, "MF2"), (MF, "MF3"), (MF, "MF4"), (FW, "FW"), (FW, "FW2"))
      val subs = List((GK, "SGK"), (DF, "SDF"), (DF, "SDF2"), (MF, "SMF"), (MF, "SMF2"), (FW, "SFW"))
      val commands = List("A new command", "Some other command", "A third command")

      val json =
        """{"team":"Team","tactic":"C",
          "starting":[{"position":"GK","name":"GK"},{"position":"DF","name":"DF"},{"position":"DF","name":"DF2"},{"position":"DF","name":"DF3"},{"position":"DF","name":"DF4"},{"position":"MF","name":"MF"},{"position":"MF","name":"MF2"},{"position":"MF","name":"MF3"},{"position":"MF","name":"MF4"},{"position":"FW","name":"FW"},{"position":"FW","name":"FW2"}],
          "subs":[{"position":"GK","name":"SGK"},{"position":"DF","name":"SDF"},{"position":"DF","name":"SDF2"},{"position":"MF","name":"SMF"},{"position":"MF","name":"SMF2"},{"position":"FW","name":"SFW"}],
          "penaltyKicker":"Kicker",
          "commands":["A new command","Some other command","A third command"]}
        """
      val teamSheet = TeamSheet.fromJson(Json.parse(json))

      teamSheet.team should be("Team")
      teamSheet.tactic should be(C)
      teamSheet.penaltyKicker should be("Kicker")
      teamSheet.starting should be(starting)
      teamSheet.subs should be(subs)
      teamSheet.commands should be(commands)

    }

    it("should convert a TeamSheet to JSON") {

      val starting = List((GK, "GK"), (DF, "DF"), (DF, "DF2"), (DF, "DF3"), (DF, "DF4"), (MF, "MF"), (MF, "MF2"), (MF, "MF3"), (MF, "MF4"), (FW, "FW"), (FW, "FW2"))
      val subs = List((GK, "SGK"), (DF, "SDF"), (DF, "SDF2"), (MF, "SMF"), (MF, "SMF2"), (FW, "SFW"))
      val commands = List("A new command", "Some other command", "A third command")
      val teamSheet = TeamSheet("Team", C, starting, subs, "Kicker", commands)
      val json = TeamSheet.toJson(teamSheet)

      (json \ "team").as[String] should be("Team")
      (json \ "tactic").as[String] should be("C")
      (json \ "penaltyKicker").as[String] should be("Kicker")

      val startingArray = (json \ "starting").as[JsArray]
      (0 until starting.length).map{ i => startingArray(i).as[(PlayerType, String)] should be(starting(i)) }

      val subsArray = (json \ "subs").as[JsArray]
      (0 until subs.length).map{ i =>  subsArray(i).as[(PlayerType, String)] should be(subs(i)) }

      val commandsArray = (json \ "commands").as[JsArray]
      (0 until commands.length).map{ i =>  commandsArray(i).as[String] should be(commands(i)) }
    }

  }

  describe("A TeamSheet class") {

    // Empty

  }

}

