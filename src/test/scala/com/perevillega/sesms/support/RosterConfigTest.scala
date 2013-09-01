package com.perevillega.sesms.support

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.Json

/**
 * Tests for RosterConfig
 * User: pvillega
 */
class RosterConfigTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A RosterConfig object") {

    it("should return the default config for rosters as RosterConfig object") {
      val config = RosterConfig.getDefaultRosterConfig()
      
      config should be(RosterConfig(3, 8, 3, 8, 3, 5, 60, 30, 14, 11, 7))
    }

    it("should load a Config from JSON") {
      val json = """{"numGoalkeepers":3,"numDefenders":4,"numDefensiveMidfielders":3,"numMidfielders":8,
                    "numAttackMidfielders":3,"numForwards":5,"averageStamina":50,"averageAggression":30,
                    "averageMainSkill":15,"averageMidSkill":12,"averageSecondarySkill":9}
                 """
      val config = RosterConfig.fromJson(Json.parse(json))

      config.numGoalkeepers should be(3)
      config.numDefenders should be(4)
      config.numDefensiveMidfielders should be(3)
      config.numMidfielders should be(8)
      config.numAttackMidfielders should be(3)
      config.numForwards should be(5)
      config.averageStamina should be(50)
      config.averageAggression should be(30)
      config.averageMainSkill should be(15)
      config.averageMidSkill should be(12)
      config.averageSecondarySkill should be(9)
    }

    it("should convert a Config to JSON") {      
      val config = RosterConfig(3, 4, 3, 8, 3, 5, 50, 30, 15, 12, 9)
      val json = RosterConfig.toJson(config)

      (json \ "numGoalkeepers").as[Int] should be(3)
      (json \ "numDefenders").as[Int] should be(4)
      (json \ "numDefensiveMidfielders").as[Int] should be(3)
      (json \ "numMidfielders").as[Int] should be(8)
      (json \ "numAttackMidfielders").as[Int] should be(3)
      (json \ "numForwards").as[Int] should be(5)
      (json \ "averageStamina").as[Int] should be(50)
      (json \ "averageAggression").as[Int] should be(30)
      (json \ "averageMainSkill").as[Int] should be(15)
      (json \ "averageMidSkill").as[Int] should be(12)
      (json \ "averageSecondarySkill").as[Int] should be(9)
    }
    
  }

  describe("A RosterConfig class") {

   // Empty

  }

}

