package com.perevillega.sesms

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.{JsArray, Json}
import com.perevillega.sesms.support.Country

/**
 * Tests for Fixtures
 * User: pvillega
 */
class FixturesTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Fixtures object") {

    it("should generate empty schedules if we don't give teams") {
      val fixtures = Fixtures.createFixtures(Nil)

      fixtures.isEmpty should be(true)
    }

    it("should generate valid schedules if we give one team") {
      val teams = List("A")
      val fixtures = Fixtures.createFixtures(teams)

      fixtures.isEmpty should be(false)
      fixtures(0).week should be(1)
      fixtures(0).games.flatten.sorted should be(List(List("A",Fixtures.placeholder)).flatten.sorted)
    }

    it("should valid generate valid schedules if the number of teams is even") {
      val teams = List("A", "B", "C", "D", "E", "F")
      val fixtures = Fixtures.createFixtures(teams)

      fixtures.isEmpty should be(false)
      fixtures.length should be(teams.length-1)
      fixtures(0).week should be(1)
      fixtures(fixtures.length-1).week should be(teams.length-1)

      fixtures.foreach{ f => f.games.flatten.sorted == teams.sorted should be(true)  }
    }

    it("should valid generate valid schedules if the number of teams is odd") {
      val teams = List("A", "B", "C", "D", "E")
      val fixtures = Fixtures.createFixtures(teams)

      fixtures.isEmpty should be(false)
      fixtures.length should be(teams.length) //consider we have the placeholder added to these fixtures
      fixtures(0).week should be(1)
      fixtures(fixtures.length-1).week should be(teams.length) //consider we have the placeholder added to these fixtures

      fixtures.foreach{ f => f.games.flatten.sorted == (Fixtures.placeholder::teams).sorted should be(true)  }

      // we have additional off days as teams are odd
      fixtures.foreach{ f => f.games.exists(l => l(0) == Fixtures.placeholder || l(1) == Fixtures.placeholder) should be(true)  }
    }

    it("should load Fixtures from JSON") {
      val json = """{"week":1,"games":[["A","B"],["C","D"]]}"""
      val fixtures = Fixtures.fromJson(Json.parse(json))

      fixtures.week should be(1)
      fixtures.games should be(List(List("A","B"), List("C", "D")))
    }

    it("should convert Fixtures to JSON") {
      val fixtures = Fixtures(1, List(List("A","B"), List("C", "D")))
      val json = Fixtures.toJson(fixtures)


      (json \ "week").as[Int] should be(1)

      val array = (json \ "games").as[JsArray]
      array(0)(0).as[String] should be("A")
      array(0)(1).as[String] should be("B")
      array(1)(0).as[String] should be("C")
      array(1)(1).as[String] should be("D")
    }
  }

  describe("A Fixtures class") {

    // Empty

  }

}

