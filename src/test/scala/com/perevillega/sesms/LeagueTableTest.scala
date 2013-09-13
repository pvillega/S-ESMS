package com.perevillega.sesms

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.{JsArray, Json}

/**
 * Tests for LeagueTable
 * User: pvillega
 */
class LeagueTableTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A LeagueTable object") {

    it("should load LeagueTable from JSON") {
      val json = """{"table":
                     {"A":{"team":"A","win":2,"draw":3,"lost":4,"goalsScored":5,"goalsReceived":6},
                      "B":{"team":"B","win":1,"draw":2,"lost":3,"goalsScored":4,"goalsReceived":5}
                      }
                    }
                 """
      val table = LeagueTable.fromJson(Json.parse(json))

      table.table("A") should be(TableEntry("A", 2, 3, 4, 5, 6))
      table.table("B") should be(TableEntry("B", 1, 2, 3, 4, 5))
    }

    it("should convert LeagueTable to JSON") {
      val map = Map( ("A" -> TableEntry("A", 2, 3, 4, 5, 6)), ("B" -> TableEntry("B", 1, 2, 3, 4, 5)) )
      val table = LeagueTable(map)
      val json = LeagueTable.toJson(table)

      val obj = (json \ "table")

      (obj \ "A").as[TableEntry] should be(TableEntry("A", 2, 3, 4, 5, 6))
      (obj \ "B").as[TableEntry] should be(TableEntry("B", 1, 2, 3, 4, 5))
    }
  }

  describe("A LeagueTable class") {

    it("should update the table given a list of results") {
      val map = Map( ("A" -> TableEntry("A", 2, 3, 4, 5, 6)), ("B" -> TableEntry("B", 1, 2, 3, 4, 5)) )
      val table = LeagueTable(map)

      val results = MatchResult("A", "B", 1, 0) :: Nil
      val newTable = table.updateTable(results)

      newTable.table("A") should be(TableEntry("A", 3, 3, 4, 6, 6))
      newTable.table("B") should be(TableEntry("B", 1, 2, 4, 4, 6))

      val results2 = MatchResult("A", "B", 3, 3) :: Nil
      val newTable2 = table.updateTable(results2)

      newTable2.table("A") should be(TableEntry("A", 2, 4, 4, 8, 9))
      newTable2.table("B") should be(TableEntry("B", 1, 3, 3, 7, 8))

      val results3 = MatchResult("A", "B", 2, 4) :: Nil
      val newTable3 = table.updateTable(results3)

      newTable3.table("A") should be(TableEntry("A", 2, 3, 5, 7, 10))
      newTable3.table("B") should be(TableEntry("B", 2, 2, 3, 8, 7))
    }

    it("should add new teams") {
      val map = Map( ("A" -> TableEntry("A", 2, 3, 4, 5, 6)), ("B" -> TableEntry("B", 1, 2, 3, 4, 5)) )
      val table = LeagueTable(map)

      val newTable = table.addTeam("C")

      newTable.table("A") should be(TableEntry("A", 2, 3, 4, 5, 6))
      newTable.table("B") should be(TableEntry("B", 1, 2, 3, 4, 5))
      newTable.table("C") should be(TableEntry("C", 0, 0, 0, 0, 0))
    }

    it("should wipe existing teams") {
      val map = Map( ("A" -> TableEntry("A", 2, 3, 4, 5, 6)), ("B" -> TableEntry("B", 1, 2, 3, 4, 5)) )
      val table = LeagueTable(map)

      val newTable = table.addTeam("A")

      newTable.table("A") should be(TableEntry("A", 0, 0, 0, 0, 0))
      newTable.table("B") should be(TableEntry("B", 1, 2, 3, 4, 5))
    }

  }

}

