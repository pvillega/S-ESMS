package com.perevillega.sesms.support

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.{JsArray, Json}
import com.perevillega.sesms.TacticType
import java.util.ResourceBundle

/**
 * Tests for Config
 * User: pvillega
 */
class ConfigTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Config object") {

    it("should load a Config from JSON") {
      val json = """{
                    "rosterConfig":
                    {
                      "numGoalkeepers":3,"numDefenders":4,"numDefensiveMidfielders":3,"numMidfielders":8,"numAttackMidfielders":3,"numForwards":5,
                      "averageStamina":50,"averageAggression":30,"averageMainSkill":15,"averageMidSkill":12,"averageSecondarySkill":9
                    },

                    "leagueConfig":
                    {
                      "homeBonus":1,"disciplinaryPointsYellowCard":2,"disciplinaryPointsRedCard":3,"suspensionMargin":4,"maxInjuryLength":5,
                      "numSubsSelected":6,"substitutions":7,"fitnessGain":8,"fitnessAfterInjury":9,
                      "abilityChanges": {
                        "abGoal":10,"abAssist":11,"abVictoryRandom":12,"abCleanSheet":13,"abKtK":14,"abKps":15,"abShtOn":16,"abShtOff":17,"abSave":18,
                        "abDefeatRandom":19,"abConcede":20,"abYellow":21,"abRed":22
                      }
                    },

                    "tacticConfig":
                    {
                      "normal":{"name":"Test","df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0},"extra":[{"tactic":"L","bonus":{"df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0}}}]},
                      "defensive":{"name":"Test","df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0},"extra":[{"tactic":"L","bonus":{"df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0}}}]},
                      "attacking":{"name":"Test","df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0},"extra":[{"tactic":"L","bonus":{"df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0}}}]},
                      "counterAttack":{"name":"Test","df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0},"extra":[{"tactic":"L","bonus":{"df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0}}}]},
                      "longBall":{"name":"Test","df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0},"extra":[{"tactic":"L","bonus":{"df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0}}}]},
                      "passing":{"name":"Test","df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0},"extra":[{"tactic":"L","bonus":{"df":{"tackle":0,"pass":0,"shoot":0},"dm":{"tackle":0,"pass":0,"shoot":0},"mf":{"tackle":0,"pass":0,"shoot":0},"am":{"tackle":0,"pass":0,"shoot":0},"fw":{"tackle":0,"pass":0,"shoot":0}}}]}
                    }
                    }
                 """
      val config = Config.fromJson(Json.parse(json))

      config.rosterConfig.numGoalkeepers should be(3)
      config.rosterConfig.numDefenders should be(4)
      config.rosterConfig.numDefensiveMidfielders should be(3)
      config.rosterConfig.numMidfielders should be(8)
      config.rosterConfig.numAttackMidfielders should be(3)
      config.rosterConfig.numForwards should be(5)
      config.rosterConfig.averageStamina should be(50)
      config.rosterConfig.averageAggression should be(30)
      config.rosterConfig.averageMainSkill should be(15)
      config.rosterConfig.averageMidSkill should be(12)
      config.rosterConfig.averageSecondarySkill should be(9)


      config.leagueConfig.homeBonus should be(1)
      config.leagueConfig.disciplinaryPointsYellowCard should be(2)
      config.leagueConfig.disciplinaryPointsRedCard should be(3)
      config.leagueConfig.suspensionMargin should be(4)
      config.leagueConfig.maxInjuryLength should be(5)
      config.leagueConfig.numSubsSelected should be(6)
      config.leagueConfig.substitutions should be(7)
      config.leagueConfig.fitnessGain should be(8)
      config.leagueConfig.fitnessAfterInjury should be(9)
      config.leagueConfig.abilityChanges.abGoal should be(10)
      config.leagueConfig.abilityChanges.abAssist should be(11)
      config.leagueConfig.abilityChanges.abVictoryRandom should be(12)
      config.leagueConfig.abilityChanges.abCleanSheet should be(13)
      config.leagueConfig.abilityChanges.abKtK should be(14)
      config.leagueConfig.abilityChanges.abKps should be(15)
      config.leagueConfig.abilityChanges.abShtOn should be(16)
      config.leagueConfig.abilityChanges.abShtOff should be(17)
      config.leagueConfig.abilityChanges.abSave should be(18)
      config.leagueConfig.abilityChanges.abDefeatRandom should be(19)
      config.leagueConfig.abilityChanges.abConcede should be(20)
      config.leagueConfig.abilityChanges.abYellow should be(21)
      config.leagueConfig.abilityChanges.abRed should be(22)

      config.tacticConfig.normal.name should be("Test")
      config.tacticConfig.normal.df.tackle should be(0)
      config.tacticConfig.normal.df.pass should be(0)
      config.tacticConfig.normal.df.shoot should be(0)
      config.tacticConfig.normal.dm.tackle should be(0)
      config.tacticConfig.normal.dm.pass should be(0)
      config.tacticConfig.normal.dm.shoot should be(0)
      config.tacticConfig.normal.mf.tackle should be(0)
      config.tacticConfig.normal.mf.pass should be(0)
      config.tacticConfig.normal.mf.shoot should be(0)
      config.tacticConfig.normal.am.tackle should be(0)
      config.tacticConfig.normal.am.pass should be(0)
      config.tacticConfig.normal.am.shoot should be(0)
      config.tacticConfig.normal.fw.tackle should be(0)
      config.tacticConfig.normal.fw.pass should be(0)
      config.tacticConfig.normal.fw.shoot should be(0)

      config.tacticConfig.normal.extra(0)._1.toString should be(TacticType.L.toString)
      config.tacticConfig.normal.extra(0)._2.df.tackle should be(0)
      config.tacticConfig.normal.extra(0)._2.df.pass should be(0)
      config.tacticConfig.normal.extra(0)._2.df.shoot should be(0)
      config.tacticConfig.normal.extra(0)._2.dm.tackle should be(0)
      config.tacticConfig.normal.extra(0)._2.dm.pass should be(0)
      config.tacticConfig.normal.extra(0)._2.dm.shoot should be(0)
      config.tacticConfig.normal.extra(0)._2.mf.tackle should be(0)
      config.tacticConfig.normal.extra(0)._2.mf.pass should be(0)
      config.tacticConfig.normal.extra(0)._2.mf.shoot should be(0)
      config.tacticConfig.normal.extra(0)._2.am.tackle should be(0)
      config.tacticConfig.normal.extra(0)._2.am.pass should be(0)
      config.tacticConfig.normal.extra(0)._2.am.shoot should be(0)
      config.tacticConfig.normal.extra(0)._2.fw.tackle should be(0)
      config.tacticConfig.normal.extra(0)._2.fw.pass should be(0)
      config.tacticConfig.normal.extra(0)._2.fw.shoot should be(0)

      config.tacticConfig.defensive.name should be("Test")
      config.tacticConfig.defensive.df.tackle should be(0)
      config.tacticConfig.defensive.df.pass should be(0)
      config.tacticConfig.defensive.df.shoot should be(0)
      config.tacticConfig.defensive.dm.tackle should be(0)
      config.tacticConfig.defensive.dm.pass should be(0)
      config.tacticConfig.defensive.dm.shoot should be(0)
      config.tacticConfig.defensive.mf.tackle should be(0)
      config.tacticConfig.defensive.mf.pass should be(0)
      config.tacticConfig.defensive.mf.shoot should be(0)
      config.tacticConfig.defensive.am.tackle should be(0)
      config.tacticConfig.defensive.am.pass should be(0)
      config.tacticConfig.defensive.am.shoot should be(0)
      config.tacticConfig.defensive.fw.tackle should be(0)
      config.tacticConfig.defensive.fw.pass should be(0)
      config.tacticConfig.defensive.fw.shoot should be(0)

      config.tacticConfig.attacking.name should be("Test")
      config.tacticConfig.attacking.df.tackle should be(0)
      config.tacticConfig.attacking.df.pass should be(0)
      config.tacticConfig.attacking.df.shoot should be(0)
      config.tacticConfig.attacking.dm.tackle should be(0)
      config.tacticConfig.attacking.dm.pass should be(0)
      config.tacticConfig.attacking.dm.shoot should be(0)
      config.tacticConfig.attacking.mf.tackle should be(0)
      config.tacticConfig.attacking.mf.pass should be(0)
      config.tacticConfig.attacking.mf.shoot should be(0)
      config.tacticConfig.attacking.am.tackle should be(0)
      config.tacticConfig.attacking.am.pass should be(0)
      config.tacticConfig.attacking.am.shoot should be(0)
      config.tacticConfig.attacking.fw.tackle should be(0)
      config.tacticConfig.attacking.fw.pass should be(0)
      config.tacticConfig.attacking.fw.shoot should be(0)

      config.tacticConfig.counterAttack.name should be("Test")
      config.tacticConfig.counterAttack.df.tackle should be(0)
      config.tacticConfig.counterAttack.df.pass should be(0)
      config.tacticConfig.counterAttack.df.shoot should be(0)
      config.tacticConfig.counterAttack.dm.tackle should be(0)
      config.tacticConfig.counterAttack.dm.pass should be(0)
      config.tacticConfig.counterAttack.dm.shoot should be(0)
      config.tacticConfig.counterAttack.mf.tackle should be(0)
      config.tacticConfig.counterAttack.mf.pass should be(0)
      config.tacticConfig.counterAttack.mf.shoot should be(0)
      config.tacticConfig.counterAttack.am.tackle should be(0)
      config.tacticConfig.counterAttack.am.pass should be(0)
      config.tacticConfig.counterAttack.am.shoot should be(0)
      config.tacticConfig.counterAttack.fw.tackle should be(0)
      config.tacticConfig.counterAttack.fw.pass should be(0)
      config.tacticConfig.counterAttack.fw.shoot should be(0)

      config.tacticConfig.longBall.name should be("Test")
      config.tacticConfig.longBall.df.tackle should be(0)
      config.tacticConfig.longBall.df.pass should be(0)
      config.tacticConfig.longBall.df.shoot should be(0)
      config.tacticConfig.longBall.dm.tackle should be(0)
      config.tacticConfig.longBall.dm.pass should be(0)
      config.tacticConfig.longBall.dm.shoot should be(0)
      config.tacticConfig.longBall.mf.tackle should be(0)
      config.tacticConfig.longBall.mf.pass should be(0)
      config.tacticConfig.longBall.mf.shoot should be(0)
      config.tacticConfig.longBall.am.tackle should be(0)
      config.tacticConfig.longBall.am.pass should be(0)
      config.tacticConfig.longBall.am.shoot should be(0)
      config.tacticConfig.longBall.fw.tackle should be(0)
      config.tacticConfig.longBall.fw.pass should be(0)
      config.tacticConfig.longBall.fw.shoot should be(0)

      config.tacticConfig.passing.name should be("Test")
      config.tacticConfig.passing.df.tackle should be(0)
      config.tacticConfig.passing.df.pass should be(0)
      config.tacticConfig.passing.df.shoot should be(0)
      config.tacticConfig.passing.dm.tackle should be(0)
      config.tacticConfig.passing.dm.pass should be(0)
      config.tacticConfig.passing.dm.shoot should be(0)
      config.tacticConfig.passing.mf.tackle should be(0)
      config.tacticConfig.passing.mf.pass should be(0)
      config.tacticConfig.passing.mf.shoot should be(0)
      config.tacticConfig.passing.am.tackle should be(0)
      config.tacticConfig.passing.am.pass should be(0)
      config.tacticConfig.passing.am.shoot should be(0)
      config.tacticConfig.passing.fw.tackle should be(0)
      config.tacticConfig.passing.fw.pass should be(0)
      config.tacticConfig.passing.fw.shoot should be(0)

    }

    it("should convert a Config to JSON") {
      val rosterConfig = RosterConfig(3, 4, 3, 8, 3, 5, 50, 30, 15, 12, 9)
      val abilityConfig = AbilityConfig(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22)
      val leagueConfig = LeagueConfig(1, 2, 3, 4, 5, 6, 7, 8, 9, abilityConfig)

      val pl = ExtraTacticBonus(PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))
      val p = TacticBonus("Test", PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), List(TacticType.L -> pl))
      val tacticConfig = TacticConfig(p, p, p, p, p, p)

      val config = Config(rosterConfig, leagueConfig, tacticConfig)
      val json = Config.toJson(config)

      (json \ "rosterConfig" \ "numGoalkeepers").as[Int] should be(3)
      (json \ "rosterConfig" \ "numDefenders").as[Int] should be(4)
      (json \ "rosterConfig" \ "numDefensiveMidfielders").as[Int] should be(3)
      (json \ "rosterConfig" \ "numMidfielders").as[Int] should be(8)
      (json \ "rosterConfig" \ "numAttackMidfielders").as[Int] should be(3)
      (json \ "rosterConfig" \ "numForwards").as[Int] should be(5)
      (json \ "rosterConfig" \ "averageStamina").as[Int] should be(50)
      (json \ "rosterConfig" \ "averageAggression").as[Int] should be(30)
      (json \ "rosterConfig" \ "averageMainSkill").as[Int] should be(15)
      (json \ "rosterConfig" \ "averageMidSkill").as[Int] should be(12)
      (json \ "rosterConfig" \ "averageSecondarySkill").as[Int] should be(9)


      (json \ "leagueConfig" \ "homeBonus").as[Int] should be(1)
      (json \ "leagueConfig" \ "disciplinaryPointsYellowCard").as[Int] should be(2)
      (json \ "leagueConfig" \ "disciplinaryPointsRedCard").as[Int] should be(3)
      (json \ "leagueConfig" \ "suspensionMargin").as[Int] should be(4)
      (json \ "leagueConfig" \ "maxInjuryLength").as[Int] should be(5)
      (json \ "leagueConfig" \ "numSubsSelected").as[Int] should be(6)
      (json \ "leagueConfig" \ "substitutions").as[Int] should be(7)
      (json \ "leagueConfig" \ "fitnessGain").as[Int] should be(8)
      (json \ "leagueConfig" \ "fitnessAfterInjury").as[Int] should be(9)
      (json \ "leagueConfig" \ "abilityChanges" \ "abGoal").as[Int] should be(10)
      (json \ "leagueConfig" \ "abilityChanges" \ "abAssist").as[Int] should be(11)
      (json \ "leagueConfig" \ "abilityChanges" \ "abVictoryRandom").as[Int] should be(12)
      (json \ "leagueConfig" \ "abilityChanges" \ "abCleanSheet").as[Int] should be(13)
      (json \ "leagueConfig" \ "abilityChanges" \ "abKtK").as[Int] should be(14)
      (json \ "leagueConfig" \ "abilityChanges" \ "abKps").as[Int] should be(15)
      (json \ "leagueConfig" \ "abilityChanges" \ "abShtOn").as[Int] should be(16)
      (json \ "leagueConfig" \ "abilityChanges" \ "abShtOff").as[Int] should be(17)
      (json \ "leagueConfig" \ "abilityChanges" \ "abSave").as[Int] should be(18)
      (json \ "leagueConfig" \ "abilityChanges" \ "abDefeatRandom").as[Int] should be(19)
      (json \ "leagueConfig" \ "abilityChanges" \ "abConcede").as[Int] should be(20)
      (json \ "leagueConfig" \ "abilityChanges" \ "abYellow").as[Int] should be(21)
      (json \ "leagueConfig" \ "abilityChanges" \ "abRed").as[Int] should be(22)

      (json \ "tacticConfig" \ "normal" \ "name").as[String] should be("Test")
      (json \ "tacticConfig" \ "normal" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "df" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "am" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "normal" \ "fw" \ "shoot").as[BigDecimal] should be(0)

      val array = (json \ "tacticConfig" \ "normal" \ "extra").as[JsArray]

      (array(0) \ "tactic").as[String] should be(TacticType.L.toString)
      (array(0) \ "bonus" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "df" \ "pass").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "am" \ "pass").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (array(0) \ "bonus" \ "fw" \ "shoot").as[BigDecimal] should be(0)

      (json \ "tacticConfig" \ "defensive" \ "name").as[String] should be("Test")
      (json \ "tacticConfig" \ "defensive" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "df" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "am" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "defensive" \ "fw" \ "shoot").as[BigDecimal] should be(0)

      (json \ "tacticConfig" \ "attacking" \ "name").as[String] should be("Test")
      (json \ "tacticConfig" \ "attacking" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "df" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "am" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "attacking" \ "fw" \ "shoot").as[BigDecimal] should be(0)

      (json \ "tacticConfig" \ "counterAttack" \ "name").as[String] should be("Test")
      (json \ "tacticConfig" \ "counterAttack" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "df" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "am" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "counterAttack" \ "fw" \ "shoot").as[BigDecimal] should be(0)

      (json \ "tacticConfig" \ "longBall" \ "name").as[String] should be("Test")
      (json \ "tacticConfig" \ "longBall" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "df" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "am" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "longBall" \ "fw" \ "shoot").as[BigDecimal] should be(0)

      (json \ "tacticConfig" \ "passing" \ "name").as[String] should be("Test")
      (json \ "tacticConfig" \ "passing" \ "df" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "df" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "df" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "dm" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "dm" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "dm" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "mf" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "mf" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "mf" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "am" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "am" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "am" \ "shoot").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "fw" \ "tackle").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "fw" \ "pass").as[BigDecimal] should be(0)
      (json \ "tacticConfig" \ "passing" \ "fw" \ "shoot").as[BigDecimal] should be(0)

    }

    it("should load from bundle") {
      val bundle = ResourceBundle.getBundle("Config")
      val config = Config.fromBundle(bundle)

      config should be (Config.getDefaultConfig)
    }

  }

  describe("A Config class") {

    // Empty

  }

  describe("A TacticBonus class") {
    val NO_BONUS = ExtraTacticBonus(PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))

    it("should find the correct bonus for tactic if it has one") {
      val extraBonus = ExtraTacticBonus(PositionBonus(1, 0, 0), PositionBonus(1, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 1))
      val extraBonus2 = ExtraTacticBonus(PositionBonus(2, 0, 0), PositionBonus(2, 0, 0), PositionBonus(2, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 2))
      val extra = List((TacticType.A, extraBonus), (TacticType.L, extraBonus2))
      val tb = TacticBonus("Tactic", PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), extra)

      tb.getBonusVs(TacticType.A) should be(extraBonus)
      tb.getBonusVs(TacticType.L) should be(extraBonus2)
      tb.getBonusVs(TacticType.C) should be(NO_BONUS)
      tb.getBonusVs(TacticType.D) should be(NO_BONUS)
      tb.getBonusVs(TacticType.N) should be(NO_BONUS)
      tb.getBonusVs(TacticType.P) should be(NO_BONUS)
    }

  }

}

