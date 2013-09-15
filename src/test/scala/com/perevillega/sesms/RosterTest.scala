package com.perevillega.sesms


import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.{JsArray, Json}
import com.perevillega.sesms.support.Country
import java.lang.IllegalArgumentException

/**
 * Tests for Roster
 * User: pvillega
 */
class RosterTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Roster object") {

    it("should generate random names of proper length") {
      val name = Roster.generateName()
      val name2 = Roster.generateName()
      val name3 = Roster.generateName()
      val name4 = Roster.generateName()

      name.size should be <= (16)
      name.head.isUpper should be(true)
      name.charAt(1) should be('.')
      name.charAt(2) should be(' ')
      name.charAt(3).isUpper should be(true)
      name.charAt(4).isUpper should be(false)

      name2.size should be <= (16)
      name2.head.isUpper should be(true)
      name2.charAt(1) should be('.')
      name2.charAt(2) should be(' ')
      name2.charAt(3).isUpper should be(true)
      name2.charAt(4).isUpper should be(false)

      name3.size should be <= (16)
      name3.head.isUpper should be(true)
      name3.charAt(1) should be('.')
      name3.charAt(2) should be(' ')
      name3.charAt(3).isUpper should be(true)
      name3.charAt(4).isUpper should be(false)

      name4.size should be <= (16)
      name4.head.isUpper should be(true)
      name4.charAt(1) should be('.')
      name4.charAt(2) should be(' ')
      name4.charAt(3).isUpper should be(true)
      name4.charAt(4).isUpper should be(false)
    }

    it("should load a Roster from JSON") {
      val json = """{"players":[
                    {"name":"A","team":"B","nationality":{"name": "a", "code": "BB"},"prefSide":"D","age":0,
                      "stats":{"stop":11,"tackle":22,"pass":33,"shoot":44,"aggression":55,"stamina":66,"stopAb":77,"tackleAb":88,"passAb":99,"shootAb":10},
                      "gamesPlayed":1,"savesDone":2,"tacklesDone":3,"keyPassesDone":4,"shootsTaken":5,"goalsScored":6,"assistsDone":7,
                      "disciplinaryPoints":8,"injuryTime":9,"suspensionTime":10,"fitness":11},
                    {"name":"A2","team":"B2","nationality":{"name": "a2", "code": "BB2"},"prefSide":"D2","age":2,
                      "stats":{"stop":112,"tackle":222,"pass":332,"shoot":442,"aggression":552,"stamina":662,"stopAb":772,"tackleAb":882,"passAb":992,"shootAb":102},
                      "gamesPlayed":12,"savesDone":22,"tacklesDone":32,"keyPassesDone":42,"shootsTaken":52,"goalsScored":62,"assistsDone":72,
                      "disciplinaryPoints":82,"injuryTime":92,"suspensionTime":102,"fitness":112}
                    ]}"""
      val roster = Roster.fromJson(Json.parse(json))

      roster.players(0).name should be("A")
      roster.players(0).team should be("B")
      roster.players(0).nationality should be(Country("a", "BB"))
      roster.players(0).prefSide should be("D")
      roster.players(0).age should be(0)
      roster.players(0).gamesPlayed should be(1)
      roster.players(0).savesDone should be(2)
      roster.players(0).tacklesDone should be(3)
      roster.players(0).keyPassesDone should be(4)
      roster.players(0).shootsTaken should be(5)
      roster.players(0).goalsScored should be(6)
      roster.players(0).assistsDone should be(7)
      roster.players(0).disciplinaryPoints should be(8)
      roster.players(0).injuryTime should be(9)
      roster.players(0).suspensionTime should be(10)
      roster.players(0).fitness should be(11)

      roster.players(0).stats should be(PlayerStats(11, 22, 33, 44, 55, 66, 77, 88, 99, 10))
      roster.players(0).stats.stop should be(11)
      roster.players(0).stats.tackle should be(22)
      roster.players(0).stats.pass should be(33)
      roster.players(0).stats.shoot should be(44)
      roster.players(0).stats.aggression should be(55)
      roster.players(0).stats.stamina should be(66)
      roster.players(0).stats.stopAb should be(77)
      roster.players(0).stats.tackleAb should be(88)
      roster.players(0).stats.passAb should be(99)
      roster.players(0).stats.shootAb should be(10)


      roster.players(1).name should be("A2")
      roster.players(1).team should be("B2")
      roster.players(1).nationality should be(Country("a2", "BB2"))
      roster.players(1).prefSide should be("D2")
      roster.players(1).age should be(2)
      roster.players(1).gamesPlayed should be(12)
      roster.players(1).savesDone should be(22)
      roster.players(1).tacklesDone should be(32)
      roster.players(1).keyPassesDone should be(42)
      roster.players(1).shootsTaken should be(52)
      roster.players(1).goalsScored should be(62)
      roster.players(1).assistsDone should be(72)
      roster.players(1).disciplinaryPoints should be(82)
      roster.players(1).injuryTime should be(92)
      roster.players(1).suspensionTime should be(102)
      roster.players(1).fitness should be(112)

      roster.players(1).stats should be(PlayerStats(112, 222, 332, 442, 552, 662, 772, 882, 992, 102))
      roster.players(1).stats.stop should be(112)
      roster.players(1).stats.tackle should be(222)
      roster.players(1).stats.pass should be(332)
      roster.players(1).stats.shoot should be(442)
      roster.players(1).stats.aggression should be(552)
      roster.players(1).stats.stamina should be(662)
      roster.players(1).stats.stopAb should be(772)
      roster.players(1).stats.tackleAb should be(882)
      roster.players(1).stats.passAb should be(992)
      roster.players(1).stats.shootAb should be(102)

    }

    it("should convert a Roster to JSON") {
      val player = Player("A", "B", Country("a", "BB"), "D", 0, PlayerStats(11, 22, 33, 44, 55, 66, 77, 88, 99, 10), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
      val player2 = Player("A2", "B2", Country("a2", "BB2"), "D2", 2, PlayerStats(112, 222, 332, 442, 552, 662, 772, 882, 992, 102), 12, 22, 32, 42, 52, 62, 72, 82, 92, 102, 112)
      val roster = Roster(player :: player2 :: Nil)
      val json = Roster.toJson(roster)

      val array = (json \ "players").as[JsArray]

      (array(0) \ "name").as[String] should be("A")
      (array(0) \ "team").as[String] should be("B")
      (array(0) \ "nationality").as[Country] should be(Country("a", "BB"))
      (array(0) \ "prefSide").as[String] should be("D")
      (array(0) \ "age").as[Int] should be(0)
      (array(0) \ "gamesPlayed").as[Int] should be(1)
      (array(0) \ "savesDone").as[Int] should be(2)
      (array(0) \ "tacklesDone").as[Int] should be(3)
      (array(0) \ "keyPassesDone").as[Int] should be(4)
      (array(0) \ "shootsTaken").as[Int] should be(5)
      (array(0) \ "goalsScored").as[Int] should be(6)
      (array(0) \ "assistsDone").as[Int] should be(7)
      (array(0) \ "disciplinaryPoints").as[Int] should be(8)
      (array(0) \ "injuryTime").as[Int] should be(9)
      (array(0) \ "suspensionTime").as[Int] should be(10)
      (array(0) \ "fitness").as[Int] should be(11)

      (array(0) \ "stats").as[PlayerStats] should be(PlayerStats(11, 22, 33, 44, 55, 66, 77, 88, 99, 10))
      (array(0) \ "stats" \ "stop").as[Int] should be(11)
      (array(0) \ "stats" \ "tackle").as[Int] should be(22)
      (array(0) \ "stats" \ "pass").as[Int] should be(33)
      (array(0) \ "stats" \ "shoot").as[Int] should be(44)
      (array(0) \ "stats" \ "aggression").as[Int] should be(55)
      (array(0) \ "stats" \ "stamina").as[Int] should be(66)
      (array(0) \ "stats" \ "stopAb").as[Int] should be(77)
      (array(0) \ "stats" \ "tackleAb").as[Int] should be(88)
      (array(0) \ "stats" \ "passAb").as[Int] should be(99)
      (array(0) \ "stats" \ "shootAb").as[Int] should be(10)


      (array(1) \ "name").as[String] should be("A2")
      (array(1) \ "team").as[String] should be("B2")
      (array(1) \ "nationality").as[Country] should be(Country("a2", "BB2"))
      (array(1) \ "prefSide").as[String] should be("D2")
      (array(1) \ "age").as[Int] should be(2)
      (array(1) \ "gamesPlayed").as[Int] should be(12)
      (array(1) \ "savesDone").as[Int] should be(22)
      (array(1) \ "tacklesDone").as[Int] should be(32)
      (array(1) \ "keyPassesDone").as[Int] should be(42)
      (array(1) \ "shootsTaken").as[Int] should be(52)
      (array(1) \ "goalsScored").as[Int] should be(62)
      (array(1) \ "assistsDone").as[Int] should be(72)
      (array(1) \ "disciplinaryPoints").as[Int] should be(82)
      (array(1) \ "injuryTime").as[Int] should be(92)
      (array(1) \ "suspensionTime").as[Int] should be(102)
      (array(1) \ "fitness").as[Int] should be(112)

      (array(1) \ "stats").as[PlayerStats] should be(PlayerStats(112, 222, 332, 442, 552, 662, 772, 882, 992, 102))
      (array(1) \ "stats" \ "stop").as[Int] should be(112)
      (array(1) \ "stats" \ "tackle").as[Int] should be(222)
      (array(1) \ "stats" \ "pass").as[Int] should be(332)
      (array(1) \ "stats" \ "shoot").as[Int] should be(442)
      (array(1) \ "stats" \ "aggression").as[Int] should be(552)
      (array(1) \ "stats" \ "stamina").as[Int] should be(662)
      (array(1) \ "stats" \ "stopAb").as[Int] should be(772)
      (array(1) \ "stats" \ "tackleAb").as[Int] should be(882)
      (array(1) \ "stats" \ "passAb").as[Int] should be(992)
      (array(1) \ "stats" \ "shootAb").as[Int] should be(102)

    }
  }

  describe("A Roster class") {

    it("should fail when creating a team sheet from empty roster") {
      val roster = Roster(Nil)
      evaluating {
        roster.selectTeamforFormation(4, 4, 2)
      } should produce[IllegalStateException]
    }

    it("should fail when creating a team sheet from roster with not enough players") {
      val players = Player("1", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("2", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("3", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("4", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("5", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("6", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Nil
      val roster = Roster(players)
      evaluating {
        roster.selectTeamforFormation(4, 4, 2)
      } should produce[IllegalStateException]
    }

    it("should fail when creating a team sheet using invalid tactic") {
      val players = Player("1", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("2", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("3", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("4", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("5", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("6", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("7", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("8", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("9", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("10", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("11", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S1", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S2", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S3", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S4", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S5", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S6", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S7", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Nil
      val roster = Roster(players)
      evaluating {
        roster.selectTeamforFormation(0, 0, 0)
      } should produce[IllegalArgumentException]
      evaluating {
        roster.selectTeamforFormation(5, 5, 5)
      } should produce[IllegalArgumentException]
      evaluating {
        roster.selectTeamforFormation(0, 99, 0)
      } should produce[IllegalArgumentException]
    }

    it("should fail when creating a team sheet using less than 1 sub") {
      val players = Player("1", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("2", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("3", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("4", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("5", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("6", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("7", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("8", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("9", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("10", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("11", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S1", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S2", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S3", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S4", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S5", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S6", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Player("S7", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
        Nil
      val roster = Roster(players)
      evaluating {
        roster.selectTeamforFormation(4, 4, 2, TacticType.C, 0)
      } should produce[IllegalArgumentException]
      evaluating {
        roster.selectTeamforFormation(4, 4, 2, TacticType.C, -1)
      } should produce[IllegalArgumentException]
    }

    it("should select team from roster using 4-4-2 and 7 subs (default)") {
      val players =
        Player("GK", "T", Country("A", "A"), "D", 0, PlayerStats(15, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 15, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 15, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 15, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 15, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 15, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 15, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("GK", "T", Country("A", "A"), "D", 0, PlayerStats(14, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 14, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 14, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 14, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 14, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 14, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 14, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Nil
      val roster = Roster(players)
      val teamSheet = roster.selectTeamforFormation(4, 4, 2)

      teamSheet.team should be("T")
      teamSheet.tactic should be(TacticType.N)
      teamSheet.commands should be(Nil)

      teamSheet.starting.length should be(11)
      (0 until teamSheet.starting.length).map {
        i => teamSheet.starting(i) should be((PlayerType.withName(players(i).name), players(i).name))
      }

      teamSheet.subs.length should be(7)
      (0 until teamSheet.subs.length).map {
        i => teamSheet.subs(i) should be((PlayerType.withName(players(i + 11).name), players(i + 11).name))
      }
    }

    it("should select team from roster using 4-2-4 and 3 subs") {
      val players =
        Player("GK", "T", Country("A", "A"), "D", 0, PlayerStats(15, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 15, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 15, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 15, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 15, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 15, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 15, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("FW", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 5, 15, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("GK", "T", Country("A", "A"), "D", 0, PlayerStats(14, 5, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("DF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 14, 5, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Player("MF", "T", Country("A", "A"), "D", 0, PlayerStats(5, 5, 14, 5, 5, 5, 5, 5, 5, 5), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) ::
          Nil
      val roster = Roster(players)
      val teamSheet = roster.selectTeamforFormation(4, 2, 4, TacticType.N, 3)

      teamSheet.team should be("T")
      teamSheet.tactic should be(TacticType.N)
      teamSheet.commands should be(Nil)

      teamSheet.starting.length should be(11)
      (0 until teamSheet.starting.length).map {
        i => teamSheet.starting(i) should be((PlayerType.withName(players(i).name), players(i).name))
      }

      teamSheet.subs.length should be(3)
      (0 until teamSheet.subs.length).map {
        i => teamSheet.subs(i) should be((PlayerType.withName(players(i + 11).name), players(i + 11).name))
      }
    }
  }

}

