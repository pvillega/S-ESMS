package com.perevillega.sesms

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.Json
import scala.util.Random

/**
 * Tests for TableEntry
 * User: pvillega
 */
class TableEntryTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A TableEntry object") {

    it("should load TableEntry from JSON") {
      val json = """{"team":"team","win":3,"draw":2,"lost":1,"goalsScored":5,"goalsReceived":6}"""
      val tableEntry = TableEntry.fromJson(Json.parse(json))

      tableEntry.team should be("team")
      tableEntry.win should be(3)
      tableEntry.draw should be(2)
      tableEntry.lost should be(1)
      tableEntry.goalsScored should be(5)
      tableEntry.goalsReceived should be(6)
    }

    it("should convert TableEntry to JSON") {
      val tableEntry = TableEntry("team", 3, 2, 1, 5, 6)
      val json = TableEntry.toJson(tableEntry)

      (json \ "team").as[String] should be("team")
      (json \ "win").as[Int] should be(3)
      (json \ "draw").as[Int] should be(2)
      (json \ "lost").as[Int] should be(1)
      (json \ "goalsScored").as[Int] should be(5)
      (json \ "goalsReceived").as[Int] should be(6)
    }
  }

  describe("A TableEntry class") {

    it("should calculate games played") {

      (1 to 1000). map { i =>
        val tableEntry = TableEntry("team", Random.nextInt(), Random.nextInt(), Random.nextInt(), 0, 0)
        tableEntry.gamesPlayed should be(tableEntry.win + tableEntry.lost + tableEntry.draw)
      }

    }

    it("should calculate goals difference") {

      (1 to 1000). map { i =>
        val tableEntry = TableEntry("team", 0, 0, 0, Random.nextInt(), Random.nextInt())
        tableEntry.goalsDifference should be(tableEntry.goalsScored - tableEntry.goalsReceived)
      }

    }

    it("should calculate team points") {

      (1 to 1000). map { i =>
        val tableEntry = TableEntry("team", Random.nextInt(), Random.nextInt(), Random.nextInt(), 0, 0)
        tableEntry.points should be(tableEntry.win * 3 + tableEntry.draw * 1)
      }

    }

  }

}

