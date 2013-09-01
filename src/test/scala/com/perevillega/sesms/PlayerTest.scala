package com.perevillega.sesms

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.Json
import com.perevillega.sesms.support.Country

/**
 * Tests for Player
 * User: pvillega
 */
class PlayerTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Player object") {

    it("should load a Player from JSON") {
      val json =
        """{"name":"A","team":"B","nationality":{"name": "a", "code": "BB"},"prefSide":"D","age":0,
           "stats":{"stop":11,"tackle":22,"pass":33,"shoot":44,"aggression":55,"stamina":66,"stopAb":77,"tackleAb":88,"passAb":99,"shootAb":10},
           "gamesPlayed":1,"savesDone":2,"tacklesDone":3,"keyPassesDone":4,"shootsTaken":5,"goalsScored":6,"assistsDone":7,
           "disciplinaryPoints":8,"injuryTime":9,"suspensionTime":10,"fitness":11}"""
      val player = Player.fromJson(Json.parse(json))

      player.name should be("A")
      player.team should be("B")
      player.nationality should be(Country("a", "BB"))
      player.prefSide should be("D")
      player.age should be(0)
      player.gamesPlayed should be(1)
      player.savesDone should be(2)
      player.tacklesDone should be(3)
      player.keyPassesDone should be(4)
      player.shootsTaken should be(5)
      player.goalsScored should be(6)
      player.assistsDone should be(7)
      player.disciplinaryPoints should be(8)
      player.injuryTime should be(9)
      player.suspensionTime should be(10)
      player.fitness should be(11)

      player.stats should be(PlayerStats(11, 22, 33, 44, 55, 66, 77, 88, 99, 10))
      player.stats.stop should be(11)
      player.stats.tackle should be(22)
      player.stats.pass should be(33)
      player.stats.shoot should be(44)
      player.stats.aggression should be(55)
      player.stats.stamina should be(66)
      player.stats.stopAb should be(77)
      player.stats.tackleAb should be(88)
      player.stats.passAb should be(99)
      player.stats.shootAb should be(10)
    }

    it("should convert a Player to JSON") {
      val country = Country.getAllCountries().head
      val player = Player("A", "B", country, "D", 0, PlayerStats(11, 22, 33, 44, 55, 66, 77, 88, 99, 10), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
      val json = Player.toJson(player)


      (json \ "name").as[String] should be("A")
      (json \ "team").as[String] should be("B")
      (json \ "nationality").as[Country] should be(country)
      (json \ "prefSide").as[String] should be("D")
      (json \ "age").as[Int] should be(0)
      (json \ "gamesPlayed").as[Int] should be(1)
      (json \ "savesDone").as[Int] should be(2)
      (json \ "tacklesDone").as[Int] should be(3)
      (json \ "keyPassesDone").as[Int] should be(4)
      (json \ "shootsTaken").as[Int] should be(5)
      (json \ "goalsScored").as[Int] should be(6)
      (json \ "assistsDone").as[Int] should be(7)
      (json \ "disciplinaryPoints").as[Int] should be(8)
      (json \ "injuryTime").as[Int] should be(9)
      (json \ "suspensionTime").as[Int] should be(10)
      (json \ "fitness").as[Int] should be(11)

      (json \ "stats").as[PlayerStats] should be(PlayerStats(11, 22, 33, 44, 55, 66, 77, 88, 99, 10))
      (json \ "stats" \ "stop").as[Int] should be(11)
      (json \ "stats" \ "tackle").as[Int] should be(22)
      (json \ "stats" \ "pass").as[Int] should be(33)
      (json \ "stats" \ "shoot").as[Int] should be(44)
      (json \ "stats" \ "aggression").as[Int] should be(55)
      (json \ "stats" \ "stamina").as[Int] should be(66)
      (json \ "stats" \ "stopAb").as[Int] should be(77)
      (json \ "stats" \ "tackleAb").as[Int] should be(88)
      (json \ "stats" \ "passAb").as[Int] should be(99)
      (json \ "stats" \ "shootAb").as[Int] should be(10)

    }
  }

  describe("A Player class") {

    // Empty

  }

}

