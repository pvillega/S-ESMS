package com.perevillega.sesms.support

import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.ResourceBundle
import org.slf4j.LoggerFactory
import com.perevillega.sesms.TacticType
import TacticType._

/**
 * Config for generating a Roster
 * @param numGoalkeepers number of goalkeepers in the roster
 * @param numDefenders number of defenders in the roster
 * @param numDefensiveMidfielders number of defensive midfielders in the roster
 * @param numMidfielders number of midfielders in the roster
 * @param numAttackMidfielders number of offensive midfielders in the roster
 * @param numForwards number of forwards in the roster
 * @param averageStamina average stamina of players
 * @param averageAggression average aggression of players
 * @param averageMainSkill average value of main skill
 * @param averageMidSkill average value of midfielder skills
 * @param averageSecondarySkill average value of secondary skill
 */
case class RosterConfig(numGoalkeepers: Int, numDefenders: Int, numDefensiveMidfielders: Int, numMidfielders: Int, numAttackMidfielders: Int,
                        numForwards: Int, averageStamina: Int, averageAggression: Int, averageMainSkill: Int, averageMidSkill: Int, averageSecondarySkill: Int) {

  def totalPlayersToGenerate = numGoalkeepers + numDefenders + numDefensiveMidfielders + numMidfielders + numAttackMidfielders + numForwards
}

object RosterConfig {
  /**
   * Support object to convert RosterConfig to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[RosterConfig]
}


/**
 * Stores ability change values for LeagueConfig
 * @param abGoal ab changes due to goals
 * @param abAssist ab changes due to assists
 * @param abVictoryRandom ab changes due to victory
 * @param abCleanSheet ab changes due to clean sheet
 * @param abKtK ab changes due to key tackles
 * @param abKps ab changes due to key passes
 * @param abShtOn ab changes due to shoots on target
 * @param abShtOff ab changes due to shoots missed
 * @param abSave ab changes due to saves
 * @param abDefeatRandom ab changes due to defeats
 * @param abConcede ab changes due to concede
 * @param abYellow ab changes due to yellow cards
 * @param abRed ab changes due to red cards
 */
case class AbilityConfig(abGoal: Int, abAssist: Int, abVictoryRandom: Int, abCleanSheet: Int, abKtK: Int, abKps: Int, abShtOn: Int, abShtOff: Int, abSave: Int,
                         abDefeatRandom: Int, abConcede: Int, abYellow: Int, abRed: Int)


object AbilityConfig {
  /**
   * Support object to convert AbilityConfig to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[AbilityConfig]
}

/**
 * Config for League matches
 * @param homeBonus bonus for home team in match actions
 * @param disciplinaryPointsYellowCard points that a yellow card adds to a player disciplinary rating
 * @param disciplinaryPointsRedCard points that a red card adds to a player disciplinary rating
 * @param suspensionMargin points needed for suspension
 * @param maxInjuryLength maximum time a player can be injured
 * @param numSubsSelected number of player subs accepted in a match
 * @param substitutions subs that can be done during a match
 * @param fitnessGain fitness gain on rest
 * @param fitnessAfterInjury fitness of a player after injury
 * @param abilityChanges config for ability value changes
 */
case class LeagueConfig(homeBonus: Int, disciplinaryPointsYellowCard: Int, disciplinaryPointsRedCard: Int, suspensionMargin: Int, maxInjuryLength: Int,
                        numSubsSelected: Int, substitutions: Int, fitnessGain: Int, fitnessAfterInjury: Int, abilityChanges: AbilityConfig)

object LeagueConfig {
  /**
   * Support object to convert LeagueConfig to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[LeagueConfig]
}


/**
 * Bonus to actions for a certain position
 * @param tackle bonus to tackles
 * @param pass bonus to passes
 * @param shoot bonus to shoots
 */
case class PositionBonus(tackle: BigDecimal, pass: BigDecimal, shoot: BigDecimal)

object PositionBonus {
  /**
   * Support object to convert PositionBonus to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[PositionBonus]
}

/**
 * Bonus for a tactic against other tactics
 * @param df action bonus for defenders
 * @param dm action bonus for defensive midfielders
 * @param mf action bonus for midfielders
 * @param am action bonus for attacking midfielders
 * @param fw action bonus for forwards
 */
case class ExtraTacticBonus(df: PositionBonus, dm: PositionBonus, mf: PositionBonus, am: PositionBonus, fw: PositionBonus)

object ExtraTacticBonus {
  /**
   * Support object to convert ExtraTacticBonus to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[ExtraTacticBonus]

}

/**
 * Bonus for a given tactic
 * @param name name of tactic
 * @param df action bonus for defenders
 * @param dm action bonus for defensive midfielders
 * @param mf action bonus for midfielders
 * @param am action bonus for attacking midfielders
 * @param fw action bonus for forwards
 * @param extra list that contains bonuses of this tactic vs specific tactic types
 */
case class TacticBonus(name: String, df: PositionBonus, dm: PositionBonus, mf: PositionBonus, am: PositionBonus, fw: PositionBonus, extra: List[(TacticType, ExtraTacticBonus)] = Nil) {

  private val NO_BONUS = ExtraTacticBonus(PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))

  def getBonusVs(tactic: TacticType): ExtraTacticBonus = extra.find(pair => tactic == pair._1) match {
    case None => NO_BONUS
    case Some(bonus) => bonus._2
  }
}

object TacticBonus {

  implicit def reads: Reads[(TacticType, ExtraTacticBonus)] =
    ((JsPath \ "tactic").read[TacticType] and
      (JsPath \ "bonus").read[ExtraTacticBonus]).tupled

  implicit def writes = new Writes[(TacticType, ExtraTacticBonus)] {
    def writes(v: (TacticType, ExtraTacticBonus)): JsValue = Json.obj(
      "tactic" -> v._1,
      "bonus" -> v._2
    )
  }

  /**
   * Support object to convert TacticBonus to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[TacticBonus]

}

/**
 * Tactic bonus for each tactic type
 * @param normal bonus for normal tactic
 * @param defensive bonus for defensive tactic
 * @param attacking bonus for attacking tactic
 * @param counterAttack bonus for counter attack tactic
 * @param longBall bonus for long ball tactic
 * @param passing bonus for passing tactic
 */
case class TacticConfig(normal: TacticBonus, defensive: TacticBonus, attacking: TacticBonus, counterAttack: TacticBonus, longBall: TacticBonus, passing: TacticBonus) {
  val N = normal
  val D = defensive
  val A = attacking
  val C = counterAttack
  val L = longBall
  val P = passing
}

object TacticConfig {
  /**
   * Support object to convert TacticConfig to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[TacticConfig]
}


/**
 * Configuration class, stores all the configuration
 * The reason we have subclasses inside is two-fold:
 * - specific configuration types help to keep compatibility with ESMS v2.7.3
 * - case classes have a limit in the number of parameters they accept that would be exceeded otherwise
 *
 * @param rosterConfig roster configuration
 * @param leagueConfig league configuration
 * @param tacticConfig tactic configuration
 */
case class Config(rosterConfig: RosterConfig, leagueConfig: LeagueConfig, tacticConfig: TacticConfig)

object Config {
  private val logger = LoggerFactory.getLogger(this.getClass)

  /**
   * Support object to convert Config to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[Config]

  /**
   * Constructs a Config from its Json version
   * @param json Json value that contains a Config
   * @return a Config object built from the json parameter
   */
  def fromJson(json: JsValue): Config = Json.fromJson[Config](json).get

  /**
   * Creates a json version of the Config
   * @return a JsValue with the Config
   */
  def toJson(config: Config): JsValue = Json.toJson(config)

  /**
   * Loads config from default ResourceBundle
   * @return Config object with the config in the default ResourceBundle
   */
  def fromBundle(): Config = {
    logger.info(s"Loading config using default bundle [Config.properties]")
    val bundle = ResourceBundle.getBundle("Config")
    fromBundle(bundle)
  }

  /**
   * Loads config from given ResourceBundle
   * @param bundle ResourceBundle that contains the config
   * @return Config object with the config in the ResourceBundle provided as param
   */
  def fromBundle(bundle: ResourceBundle): Config = {
    logger.info(s"Loading config using bundle [$bundle]")

    logger.debug(s"Creating Roster config instance")
    val nGk = bundle.getString("num.goalkeepers").toInt
    val nDf = bundle.getString("num.defenders").toInt
    val nDMf = bundle.getString("num.midfielders.defensive").toInt
    val nMf = bundle.getString("num.midfielders").toInt
    val nAMf = bundle.getString("num.midfielders.attacking").toInt
    val nFw = bundle.getString("num.forwards").toInt
    val avSta = bundle.getString("average.stamina").toInt
    val avAgr = bundle.getString("average.aggression").toInt
    val avSkMain = bundle.getString("average.skill.main").toInt
    val avSkMid = bundle.getString("average.skill.mid").toInt
    val avSkSec = bundle.getString("average.skill.secondary").toInt

    val roster = RosterConfig(nGk, nDf, nDMf, nMf, nAMf, nFw, avSta, avAgr, avSkMain, avSkMid, avSkSec)

    logger.debug(s"Creating League config instance")
    val homeBonus = bundle.getString("home.bonus").toInt
    val disciplinaryPointsYellowCard = bundle.getString("dp.yellow").toInt
    val disciplinaryPointsRedCard = bundle.getString("dp.red").toInt
    val suspensionMargin = bundle.getString("suspension.margin").toInt
    val maxInjuryLength = bundle.getString("max.injury.length").toInt
    val numSubsSelected = bundle.getString("num.subs").toInt
    val substitutions = bundle.getString("substitutions").toInt
    val fitnessGain = bundle.getString("fitness.gain").toInt
    val fitnessAfterInjury = bundle.getString("fitness.injury").toInt
    val abGoal = bundle.getString("ab.goal").toInt
    val abAssist = bundle.getString("ab.assist").toInt
    val abVictoryRandom = bundle.getString("ab.victory").toInt
    val abCleanSheet = bundle.getString("ab.cleansheet").toInt
    val abKtK = bundle.getString("ab.key.tackle").toInt
    val abKps = bundle.getString("ab.key.pass").toInt
    val abShtOn = bundle.getString("ab.sht.on").toInt
    val abShtOff = bundle.getString("ab.sht.off").toInt
    val abSave = bundle.getString("ab.sav").toInt
    val abDefeatRandom = bundle.getString("ab.defeat").toInt
    val abConcede = bundle.getString("ab.concede").toInt
    val abYellow = bundle.getString("ab.yellow").toInt
    val abRed = bundle.getString("ab.red").toInt

    val abilityConfig = AbilityConfig(abGoal, abAssist, abVictoryRandom, abCleanSheet, abKtK, abKps, abShtOn, abShtOff, abSave,
      abDefeatRandom, abConcede, abYellow, abRed)
    val leagueConfig = LeagueConfig(homeBonus, disciplinaryPointsYellowCard, disciplinaryPointsRedCard, suspensionMargin, maxInjuryLength,
      numSubsSelected, substitutions, fitnessGain, fitnessAfterInjury, abilityConfig)

    logger.debug(s"Creating Tactic config instance")
    val nName = bundle.getString("tactic.N")

    val ndftk = BigDecimal(bundle.getString("N.DF.TK"))
    val ndfps = BigDecimal(bundle.getString("N.DF.PS"))
    val ndfsh = BigDecimal(bundle.getString("N.DF.SH"))

    val ndf = PositionBonus(ndftk, ndfps, ndfsh)

    val ndmtk = BigDecimal(bundle.getString("N.DM.TK"))
    val ndmps = BigDecimal(bundle.getString("N.DM.PS"))
    val ndmsh = BigDecimal(bundle.getString("N.DM.SH"))

    val ndm = PositionBonus(ndmtk, ndmps, ndmsh)

    val nmftk = BigDecimal(bundle.getString("N.MF.TK"))
    val nmfps = BigDecimal(bundle.getString("N.MF.PS"))
    val nmfsh = BigDecimal(bundle.getString("N.MF.SH"))

    val nmf = PositionBonus(nmftk, nmfps, nmfsh)

    val namtk = BigDecimal(bundle.getString("N.AM.TK"))
    val namps = BigDecimal(bundle.getString("N.AM.PS"))
    val namsh = BigDecimal(bundle.getString("N.AM.SH"))

    val nam = PositionBonus(namtk, namps, namsh)

    val nfwtk = BigDecimal(bundle.getString("N.FW.TK"))
    val nfwps = BigDecimal(bundle.getString("N.FW.PS"))
    val nfwsh = BigDecimal(bundle.getString("N.FW.SH"))

    val nfw = PositionBonus(nfwtk, nfwps, nfwsh)

    val n = TacticBonus(nName, ndf, ndm, nmf, nam, nfw)

    val dName = bundle.getString("tactic.D")

    val ddftk = BigDecimal(bundle.getString("D.DF.TK"))
    val ddfps = BigDecimal(bundle.getString("D.DF.PS"))
    val ddfsh = BigDecimal(bundle.getString("D.DF.SH"))

    val ddf = PositionBonus(ddftk, ddfps, ddfsh)

    val ddmtk = BigDecimal(bundle.getString("D.DM.TK"))
    val ddmps = BigDecimal(bundle.getString("D.DM.PS"))
    val ddmsh = BigDecimal(bundle.getString("D.DM.SH"))

    val ddm = PositionBonus(ddmtk, ddmps, ddmsh)

    val dmftk = BigDecimal(bundle.getString("D.MF.TK"))
    val dmfps = BigDecimal(bundle.getString("D.MF.PS"))
    val dmfsh = BigDecimal(bundle.getString("D.MF.SH"))

    val dmf = PositionBonus(dmftk, dmfps, dmfsh)

    val damtk = BigDecimal(bundle.getString("D.AM.TK"))
    val damps = BigDecimal(bundle.getString("D.AM.PS"))
    val damsh = BigDecimal(bundle.getString("D.AM.SH"))

    val dam = PositionBonus(damtk, damps, damsh)

    val dfwtk = BigDecimal(bundle.getString("D.FW.TK"))
    val dfwps = BigDecimal(bundle.getString("D.FW.PS"))
    val dfwsh = BigDecimal(bundle.getString("D.FW.SH"))

    val dfw = PositionBonus(dfwtk, dfwps, dfwsh)

    val dldftk = BigDecimal(bundle.getString("BONUS.D.L.DF.TK"))

    val dl = ExtraTacticBonus(PositionBonus(dldftk, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))

    val d = TacticBonus(dName, ddf, ddm, dmf, dam, dfw, List(TacticType.L -> dl))

    val aName = bundle.getString("tactic.A")

    val adftk = BigDecimal(bundle.getString("A.DF.TK"))
    val adfps = BigDecimal(bundle.getString("A.DF.PS"))
    val adfsh = BigDecimal(bundle.getString("A.DF.SH"))

    val adf = PositionBonus(adftk, adfps, adfsh)

    val admtk = BigDecimal(bundle.getString("A.DM.TK"))
    val admps = BigDecimal(bundle.getString("A.DM.PS"))
    val admsh = BigDecimal(bundle.getString("A.DM.SH"))

    val adm = PositionBonus(admtk, admps, admsh)

    val amftk = BigDecimal(bundle.getString("A.MF.TK"))
    val amfps = BigDecimal(bundle.getString("A.MF.PS"))
    val amfsh = BigDecimal(bundle.getString("A.MF.SH"))

    val amf = PositionBonus(amftk, amfps, amfsh)

    val aamtk = BigDecimal(bundle.getString("A.AM.TK"))
    val aamps = BigDecimal(bundle.getString("A.AM.PS"))
    val aamsh = BigDecimal(bundle.getString("A.AM.SH"))

    val aam = PositionBonus(aamtk, aamps, aamsh)

    val afwtk = BigDecimal(bundle.getString("A.FW.TK"))
    val afwps = BigDecimal(bundle.getString("A.FW.PS"))
    val afwsh = BigDecimal(bundle.getString("A.FW.SH"))

    val afw = PositionBonus(afwtk, afwps, afwsh)

    val a = TacticBonus(aName, adf, adm, amf, aam, afw)

    val cName = bundle.getString("tactic.C")

    val cdftk = BigDecimal(bundle.getString("C.DF.TK"))
    val cdfps = BigDecimal(bundle.getString("C.DF.PS"))
    val cdfsh = BigDecimal(bundle.getString("C.DF.SH"))

    val cdf = PositionBonus(cdftk, cdfps, cdfsh)

    val cdmtk = BigDecimal(bundle.getString("C.DM.TK"))
    val cdmps = BigDecimal(bundle.getString("C.DM.PS"))
    val cdmsh = BigDecimal(bundle.getString("C.DM.SH"))

    val cdm = PositionBonus(cdmtk, cdmps, cdmsh)

    val cmftk = BigDecimal(bundle.getString("C.MF.TK"))
    val cmfps = BigDecimal(bundle.getString("C.MF.PS"))
    val cmfsh = BigDecimal(bundle.getString("C.MF.SH"))

    val cmf = PositionBonus(cmftk, cmfps, cmfsh)

    val camtk = BigDecimal(bundle.getString("C.AM.TK"))
    val camps = BigDecimal(bundle.getString("C.AM.PS"))
    val camsh = BigDecimal(bundle.getString("C.AM.SH"))

    val cam = PositionBonus(camtk, camps, camsh)

    val cfwtk = BigDecimal(bundle.getString("C.FW.TK"))
    val cfwps = BigDecimal(bundle.getString("C.FW.PS"))
    val cfwsh = BigDecimal(bundle.getString("C.FW.SH"))

    val cfw = PositionBonus(cfwtk, cfwps, cfwsh)

    val camfsh = BigDecimal(bundle.getString("BONUS.C.A.MF.SH"))
    val cadfps = BigDecimal(bundle.getString("BONUS.C.A.DF.PS"))
    val cadfsh = BigDecimal(bundle.getString("BONUS.C.A.DF.SH"))

    val ca = ExtraTacticBonus(PositionBonus(0, cadfps, cadfsh), PositionBonus(0, 0, 0), PositionBonus(0, 0, camfsh), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))

    val cpmfsh = BigDecimal(bundle.getString("BONUS.C.P.MF.SH"))
    val cpdfps = BigDecimal(bundle.getString("BONUS.C.P.DF.PS"))
    val cpdfsh = BigDecimal(bundle.getString("BONUS.C.P.DF.SH"))

    val cp = ExtraTacticBonus(PositionBonus(0, cpdfps, cpdfsh), PositionBonus(0, 0, 0), PositionBonus(0, 0, cpmfsh), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))

    val c = TacticBonus(cName, cdf, cdm, cmf, cam, cfw, List(TacticType.A -> ca, TacticType.P -> cp))

    val lName = bundle.getString("tactic.L")

    val ldftk = BigDecimal(bundle.getString("L.DF.TK"))
    val ldfps = BigDecimal(bundle.getString("L.DF.PS"))
    val ldfsh = BigDecimal(bundle.getString("L.DF.SH"))

    val ldf = PositionBonus(ldftk, ldfps, ldfsh)

    val ldmtk = BigDecimal(bundle.getString("L.DM.TK"))
    val ldmps = BigDecimal(bundle.getString("L.DM.PS"))
    val ldmsh = BigDecimal(bundle.getString("L.DM.SH"))

    val ldm = PositionBonus(ldmtk, ldmps, ldmsh)

    val lmftk = BigDecimal(bundle.getString("L.MF.TK"))
    val lmfps = BigDecimal(bundle.getString("L.MF.PS"))
    val lmfsh = BigDecimal(bundle.getString("L.MF.SH"))

    val lmf = PositionBonus(lmftk, lmfps, lmfsh)

    val lamtk = BigDecimal(bundle.getString("L.AM.TK"))
    val lamps = BigDecimal(bundle.getString("L.AM.PS"))
    val lamsh = BigDecimal(bundle.getString("L.AM.SH"))

    val lam = PositionBonus(lamtk, lamps, lamsh)

    val lfwtk = BigDecimal(bundle.getString("L.FW.TK"))
    val lfwps = BigDecimal(bundle.getString("L.FW.PS"))
    val lfwsh = BigDecimal(bundle.getString("L.FW.SH"))

    val lfw = PositionBonus(lfwtk, lfwps, lfwsh)

    val lcdftk = BigDecimal(bundle.getString("BONUS.L.C.DF.TK"))
    val lcdfps = BigDecimal(bundle.getString("BONUS.L.C.DF.PS"))

    val lc = ExtraTacticBonus(PositionBonus(lcdftk, lcdfps, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))

    val l = TacticBonus(lName, ldf, ldm, lmf, lam, lfw, List(TacticType.C -> lc))

    val pName = bundle.getString("tactic.P")

    val pdftk = BigDecimal(bundle.getString("P.DF.TK"))
    val pdfps = BigDecimal(bundle.getString("P.DF.PS"))
    val pdfsh = BigDecimal(bundle.getString("P.DF.SH"))

    val pdf = PositionBonus(pdftk, pdfps, pdfsh)

    val pdmtk = BigDecimal(bundle.getString("P.DM.TK"))
    val pdmps = BigDecimal(bundle.getString("P.DM.PS"))
    val pdmsh = BigDecimal(bundle.getString("P.DM.SH"))

    val pdm = PositionBonus(pdmtk, pdmps, pdmsh)

    val pmftk = BigDecimal(bundle.getString("P.MF.TK"))
    val pmfps = BigDecimal(bundle.getString("P.MF.PS"))
    val pmfsh = BigDecimal(bundle.getString("P.MF.SH"))

    val pmf = PositionBonus(pmftk, pmfps, pmfsh)

    val pamtk = BigDecimal(bundle.getString("P.AM.TK"))
    val pamps = BigDecimal(bundle.getString("P.AM.PS"))
    val pamsh = BigDecimal(bundle.getString("P.AM.SH"))

    val pam = PositionBonus(pamtk, pamps, pamsh)

    val pfwtk = BigDecimal(bundle.getString("P.FW.TK"))
    val pfwps = BigDecimal(bundle.getString("P.FW.PS"))
    val pfwsh = BigDecimal(bundle.getString("P.FW.SH"))

    val pfw = PositionBonus(pfwtk, pfwps, pfwsh)

    val plmfsh = BigDecimal(bundle.getString("BONUS.P.L.MF.SH"))
    val plmftk = BigDecimal(bundle.getString("BONUS.P.L.MF.TK"))
    val plfwsh = BigDecimal(bundle.getString("BONUS.P.L.FW.SH"))

    val pl = ExtraTacticBonus(PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(plmftk, 0, plmfsh), PositionBonus(0, 0, 0), PositionBonus(0, 0, plfwsh))

    val p = TacticBonus(pName, pdf, pdm, pmf, pam, pfw, List(TacticType.L -> pl))

    val tacticConfig = TacticConfig(n, d, a, c, l, p)

    logger.debug(s"Creating Config instance")
    Config(roster, leagueConfig, tacticConfig)
  }

  /**
   * Returns the default Config as per ESMS 2.7.3
   * @return default Config as per ESMS 2.7.3
   */
  def getDefaultConfig: Config = {
    val rosterConfig = RosterConfig(3, 8, 3, 8, 3, 5, 50, 30, 15, 12, 9)
    val abilityConfig = AbilityConfig(50, 35, 60, 50, 18, 12, 2, 0, 12, -60, -8, -8, -20)
    val leagueConfig = LeagueConfig(200, 4, 10, 10, 9, 5, 3, 20, 80, abilityConfig)

    val n = TacticBonus("Normal", PositionBonus(1.0, 0.5, 0.3), PositionBonus(0.85, 0.75, 0.3), PositionBonus(0.3, 1.0, 0.3), PositionBonus(0.3, 0.85, 0.85), PositionBonus(0.3, 0.3, 1.0))

    val dl = ExtraTacticBonus(PositionBonus(0.25, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))
    val d = TacticBonus("Defensive", PositionBonus(1.25, 0.5, 0.25), PositionBonus(1.13, 0.68, 0.25), PositionBonus(1.0, 0.75, 0.25), PositionBonus(0.75, 0.65, 0.5), PositionBonus(0.5, 0.25, 0.75), List(TacticType.L -> dl))

    val a = TacticBonus("Attacking", PositionBonus(1.0, 0.5, 0.5), PositionBonus(0.5, 0.75, 0.68), PositionBonus(0, 1.0, 0.75), PositionBonus(0, 0.87, 1.13), PositionBonus(0, 0.75, 1.5))

    val ca = ExtraTacticBonus(PositionBonus(0, 0.25, 0.25), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0.5), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))
    val cp = ExtraTacticBonus(PositionBonus(0, 0.25, 0.25), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0.5), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))
    val c = TacticBonus("Counter Attack", PositionBonus(1.0, 0.5, 0.25), PositionBonus(0.85, 0.85, 0.25), PositionBonus(0.5, 1.0, 0.25), PositionBonus(0.5, 0.85, 0.65), PositionBonus(0.5, 0.5, 1.0), List(TacticType.A -> ca, TacticType.P -> cp))

    val lc = ExtraTacticBonus(PositionBonus(0.25, 0.5, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0))
    val l = TacticBonus("Long Ball", PositionBonus(1.0, 0.25, 0.25), PositionBonus(0.75, 0.85, 0.38), PositionBonus(0.5, 1.0, 0.5), PositionBonus(0.45, 0.85, 0.9), PositionBonus(0.25, 0.5, 1.3), List(TacticType.C -> lc))

    val pl = ExtraTacticBonus(PositionBonus(0, 0, 0), PositionBonus(0, 0, 0), PositionBonus(0.5, 0, 0.5), PositionBonus(0, 0, 0), PositionBonus(0, 0, 0.25))
    val p = TacticBonus("Passing", PositionBonus(1.0, 0.75, 0.3), PositionBonus(0.87, 0.87, 0.28), PositionBonus(0.25, 1.0, 0.25), PositionBonus(0.25, 0.87, 0.68), PositionBonus(0.25, 0.75, 1.0), List(TacticType.L -> pl))

    val tacticConfig = TacticConfig(n, d, a, c, l, p)

    Config(rosterConfig, leagueConfig, tacticConfig)
  }
}


