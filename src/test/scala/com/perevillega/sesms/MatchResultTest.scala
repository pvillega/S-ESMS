package com.perevillega.sesms

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.{JsArray, Json}

/**
 * Tests for MatchResult
 * User: pvillega
 */
class MatchResultTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A MatchResult object") {

    it("should load MatchResult from JSON") {
      val json = """{"homeTeam":"home","awayTeam":"away","goalsHome":3,"goalsAway":5}"""
      val mr = MatchResult.fromJson(Json.parse(json))

      mr.homeTeam should be("home")
      mr.awayTeam should be("away")
      mr.goalsHome should be(3)
      mr.goalsAway should be(5)
    }

    it("should convert MatchResult to JSON") {
      val mr = MatchResult("home", "away", 3, 5)
      val json = MatchResult.toJson(mr)

      (json \ "homeTeam").as[String] should be("home")
      (json \ "awayTeam").as[String] should be("away")
      (json \ "goalsHome").as[Int] should be(3)
      (json \ "goalsAway").as[Int] should be(5)
    }
  }

  describe("A MatchResult class") {

    // Empty

  }

}

