package com.perevillega.sesms

import play.api.libs.json._
import play.api.libs.functional.syntax._
import com.perevillega.sesms.support.{EnumUtils, RandomUtils, Country, RosterConfig}
import scala.util.Random
import org.slf4j.LoggerFactory
import com.perevillega.sesms.PlayerType.PlayerType
import com.perevillega.sesms.TacticType.TacticType

/**
 * Enum used to define the tactic type selected
 */
object TacticType extends Enumeration {
  type TacticType = Value

  val N = Value("N")
  val D = Value("D")
  val A = Value("A")
  val C = Value("C")
  val L = Value("L")
  val P = Value("P")

  implicit val enumReads: Reads[TacticType] = EnumUtils.enumReads(TacticType)

  implicit def enumWrites: Writes[TacticType] = EnumUtils.enumWrites

}


/**
 * Enum used when creating roster to identify type of player
 * After generation players are not identified in a specific role, managers can use them anywhere
 */
object PlayerType extends Enumeration {
  type PlayerType = Value

  val GK = Value("GK")
  val DF = Value("DF")
  val MF = Value("MF")
  val DMF = Value("DMF")
  val AMF = Value("AMF")
  val FW = Value("FW")

  implicit val enumReads: Reads[PlayerType] = EnumUtils.enumReads(PlayerType)

  implicit def enumWrites: Writes[PlayerType] = EnumUtils.enumWrites
}


/**
 * A player in the roster
 * @param name name of the player
 * @param team team of the player
 * @param nationality nationality of the player
 * @param prefSide preferred side of the field
 * @param age age of the player
 * @param stats player stats
 * @param gamesPlayed games played
 * @param savesDone saves done
 * @param tacklesDone tackles done
 * @param keyPassesDone key passes done
 * @param shootsTaken shots done
 * @param goalsScored goals scored
 * @param assistsDone assists done
 * @param disciplinaryPoints disciplinary points
 * @param injuryTime injury time
 * @param suspensionTime suspension time
 * @param fitness fitness values
 */
case class Player(name: String, team: String, nationality: Country, prefSide: String, age: Int,
                  stats: PlayerStats,
                  gamesPlayed: Int, savesDone: Int, tacklesDone: Int, keyPassesDone: Int, shootsTaken: Int, goalsScored: Int, assistsDone: Int,
                  disciplinaryPoints: Int, injuryTime: Int, suspensionTime: Int, fitness: Int)

/**
 * Stats of a player
 * @param stop stopping skill
 * @param tackle tackle skill
 * @param pass pass skill
 * @param shoot shoot skill
 * @param aggression aggression score
 * @param stamina stamina value
 * @param stopAb stopping ability
 * @param tackleAb tackle ability
 * @param passAb pass ability
 * @param shootAb shoot ability
 */
case class PlayerStats(stop: Int, tackle: Int, pass: Int, shoot: Int, aggression: Int, stamina: Int,
                       stopAb: Int, tackleAb: Int, passAb: Int, shootAb: Int)


object PlayerStats {
  /**
   * Support object to convert PlayerStats to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[PlayerStats]
}


object Player {
  /**
   * Support object to convert Player to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[Player]

  /**
   * Constructs a Player from its Json version
   * @param json Json value that contains a Player
   * @return a Player object built from the json parameter
   */
  def fromJson(json: JsValue): Player = Json.fromJson[Player](json).get

  /**
   * Creates a json version of the Player
   * @param player the Player object to convert to json
   * @return a JsValue with the Player
   */
  def toJson(player: Player): JsValue = Json.toJson(player)
}


/**
 * A TeamSheet, used to define a team that will play a match, as per ESMS 2.7.3 specification
 * @param team name of the team
 * @param tactic selected tactic (normal, defensive, etc)
 * @param starting starting eleven, by position
 * @param subs subs, by position
 * @param penaltyKicker player selected to shoot penalties
 * @param commands commands to execute during the game simulation
 */
case class TeamSheet(team: String, tactic: TacticType, starting: List[(PlayerType, String)], subs: List[(PlayerType, String)], penaltyKicker: String, commands: List[String])


object TeamSheet {

  implicit def reads: Reads[(PlayerType, String)] =
    ((JsPath \ "position").read[PlayerType] and
      (JsPath \ "name").read[String]).tupled

  implicit def writes = new Writes[(PlayerType, String)] {
    def writes(v: (PlayerType, String)): JsValue = Json.obj(
      "position" -> v._1,
      "name" -> v._2
    )
  }

  /**
   * Support object to convert TeamSheet to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[TeamSheet]

  /**
   * Constructs a TeamSheet from its Json version
   * @param json Json value that contains a TeamSheet
   * @return a TeamSheet object built from the json parameter
   */
  def fromJson(json: JsValue): TeamSheet = Json.fromJson[TeamSheet](json).get

  /**
   * Creates a json version of the TeamSheet
   * @param teamSheet the TeamSheet object to convert to json
   * @return a JsValue with the TeamSheet
   */
  def toJson(teamSheet: TeamSheet): JsValue = Json.toJson(teamSheet)
}

/**
 * The roster of a team
 * @param players players that belong to the roster
 */
case class Roster(players: List[Player]) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  /**
   * Creates a default team sheet given the current roster, as per TSC in ESMS 2.7.3
   * Will throw exception if the roster doesn't contain enough players or the tactic doesn't add to 10
   * @param defenders number of defenders
   * @param midfielders number of midfielders
   * @param attackers number of attackers
   * @param tactic tactic type, defaults to N
   * @param numSubs number of subs to choose, defaults to 7
   */
  def selectTeamforFormation(defenders: Int, midfielders: Int, attackers: Int, tactic: TacticType = TacticType.N, numSubs: Int = 7): TeamSheet = {
    logger.info(s"Roster.selectTeamForFormation($defenders, $midfielders, $attackers, $tactic, $numSubs) - Start")
    if (defenders + midfielders + attackers != 10) throw new IllegalArgumentException("Illegal Tactic. Tactic has to total 10, ex: 4-4-2.")
    if (numSubs < 1) throw new IllegalArgumentException("Illegal subs: you need at least 1 sub (goalkeeper)")
    if (players.length < defenders + midfielders + attackers + numSubs + 1) throw new IllegalStateException("List of Players in roster doesn't have enough players to create the Team Sheet")

    val gk = players.sortBy(-_.stats.stop).take(1).map(p => (PlayerType.GK, p.name))
    logger.debug(s"GK - $gk")
    val df = players.filterNot(p => gk contains p).sortBy(-_.stats.tackle).take(defenders).map(p => (PlayerType.DF, p.name))
    logger.debug(s"DF - $df")
    val mf = players.filterNot(p => gk.contains(p) || df.contains(p)).sortBy(-_.stats.pass).take(midfielders).map(p => (PlayerType.MF, p.name))
    logger.debug(s"MF - $mf")
    val fw = players.filterNot(p => gk.contains(p) || df.contains(p) || mf.contains(p)).sortBy(-_.stats.shoot).take(attackers).map(p => (PlayerType.FW, p.name))
    logger.debug(s"FW - $fw")
    val starting = gk ::: df ::: mf ::: fw

    val possibleSubs = players.filterNot(p => starting contains p)
    val addSubs = numSubs - 1
    val dfSub = (addSubs / 5) * 2 + (if (addSubs % 5 > 2) 2 else if (addSubs % 5 > 0) 1 else 0)
    val mfSub = (addSubs / 5) * 2 + (if (addSubs % 5 > 4) 2 else if (addSubs % 5 > 1) 1 else 0)
    val fwSub = (addSubs / 5) * 1 + (if (addSubs % 5 > 3) 1 else 0)
    logger.debug(s"Subs: DF - $dfSub  MF - $mfSub  FW - $fwSub")

    val sgk = possibleSubs.sortBy(-_.stats.stop).take(1).map(p => (PlayerType.GK, p.name))
    logger.debug(s"SGK - $sgk")
    val sdf = possibleSubs.filterNot(p => sgk contains p).sortBy(-_.stats.tackle).take(dfSub).map(p => (PlayerType.DF, p.name))
    logger.debug(s"SDF - $sdf")
    val smf = possibleSubs.filterNot(p => sgk.contains(p) || sdf.contains(p)).sortBy(-_.stats.pass).take(mfSub).map(p => (PlayerType.MF, p.name))
    logger.debug(s"SMF - $smf")
    val sfw = possibleSubs.filterNot(p => sgk.contains(p) || sdf.contains(p) || smf.contains(p)).sortBy(-_.stats.shoot).take(fwSub).map(p => (PlayerType.FW, p.name))
    logger.debug(s"SFW - $sfw")
    val subs = sgk ::: sdf ::: smf ::: sfw

    val kicker = starting.last._2
    logger.debug(s"Kicker - $kicker")

    val teamSheet = TeamSheet(players(0).team, tactic, starting, subs, kicker, Nil)
    logger.info(s"Roster.selectTeamForFormation($defenders, $midfielders, $attackers, $tactic, $numSubs) - Result[$teamSheet]")
    teamSheet
  }

}


object Roster {
  private val logger = LoggerFactory.getLogger(this.getClass)

  import PlayerType._

  /**
   * Support object to convert Roster to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[Roster]

  /**
   * Constructs a Roster from its Json version
   * @param json Json value that contains a Roster
   * @return a Roster object built from the json parameter
   */
  def fromJson(json: JsValue): Roster = Json.fromJson[Roster](json).get

  /**
   * Creates a json version of the Roster
   * @param roster the Roster object to convert to json
   * @return a JsValue with the Roster
   */
  def toJson(roster: Roster): JsValue = Json.toJson(roster)

  /**
   * Creates a new Roster
   * @param config configuration to use when generating the roster
   * @param team to which team does the roster belong
   */
  def createRoster(config: RosterConfig, team: String): Roster = {
    logger.info(s"Roster.createRoster($config, $team)")
    // generate players
    val goalkeepers = (1 to config.numGoalkeepers).map(i => generateRandomPlayer(config, team, GK)).toList
    val defenders = (1 to config.numDefenders).map(i => generateRandomPlayer(config, team, DF)).toList
    val defensiveMid = (1 to config.numDefensiveMidfielders).map(i => generateRandomPlayer(config, team, DMF)).toList
    val midfielders = (1 to config.numMidfielders).map(i => generateRandomPlayer(config, team, MF)).toList
    val attackingMid = (1 to config.numAttackMidfielders).map(i => generateRandomPlayer(config, team, AMF)).toList
    val forwards = (1 to config.numForwards).map(i => generateRandomPlayer(config, team, FW)).toList

    val roster = Roster(goalkeepers ::: defenders ::: defensiveMid ::: midfielders ::: attackingMid ::: forwards)
    logger.info(s"Roster.createRoster($config, $team) - Roster: [$roster]")
    roster
  }

  /**
   * Generates a random player
   * @param config configuration to use when generating the player
   * @param team to which team does the player belong
   * @param playerType type of player to generate
   * @return a random player
   */
  protected[sesms] def generateRandomPlayer(config: RosterConfig, team: String, playerType: PlayerType): Player = {
    logger.debug(s"Roster.generateRandomPlayer($config, $team, $playerType)")
    val name = generateName()
    val country = Country.getRandomCountry()
    val age = RandomUtils.averagedRandom(23, 7)
    val prefSide = genPlayerPrefSide()
    val stats = genPlayerStats(config, playerType)

    val player = Player(name, team, country, prefSide, age, stats, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100)
    logger.debug(s"Roster.generateRandomPlayer($config, $team, $playerType) - Player:[$player]")
    player
  }

  /**
   * Generates stats for a player
   * @param config roster configuration settings
   * @param playerType type of player to generate
   * @return stats for a player
   */
  protected[sesms] def genPlayerStats(config: RosterConfig, playerType: PlayerType): PlayerStats = {
    logger.debug(s"Roster.generatePlayerStats($config, $playerType)")
    val half_average_secondary_skill = config.averageSecondarySkill / 2

    // skills based on player type
    val (st, tk, ps, sh) = playerType match {
      case GK => {
        logger.debug(s"Generating GK stats")
        val st = RandomUtils.averagedRandomPartDev(config.averageMainSkill, 3)
        val tk = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val ps = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val sh = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        (st, tk, ps, sh)
      }
      case DF => {
        logger.debug(s"Generating DF stats")
        val st = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val tk = RandomUtils.averagedRandomPartDev(config.averageMainSkill, 3)
        val ps = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        val sh = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        (st, tk, ps, sh)
      }
      case DMF => {
        logger.debug(s"Generating DMF stats")
        val st = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val tk = RandomUtils.averagedRandomPartDev(config.averageMidSkill, 3)
        val ps = RandomUtils.averagedRandomPartDev(config.averageMidSkill, 3)
        val sh = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        (st, tk, ps, sh)
      }
      case MF => {
        logger.debug(s"Generating MF stats")
        val st = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val tk = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        val ps = RandomUtils.averagedRandomPartDev(config.averageMainSkill, 3)
        val sh = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        (st, tk, ps, sh)
      }
      case AMF => {
        logger.debug(s"Generating AMF stats")
        val st = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val tk = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        val ps = RandomUtils.averagedRandomPartDev(config.averageMidSkill, 3)
        val sh = RandomUtils.averagedRandomPartDev(config.averageMidSkill, 3)
        (st, tk, ps, sh)
      }
      case FW => {
        logger.debug(s"Generating FW stats")
        val st = RandomUtils.averagedRandomPartDev(half_average_secondary_skill, 2)
        val tk = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        val ps = RandomUtils.averagedRandomPartDev(config.averageSecondarySkill, 2)
        val sh = RandomUtils.averagedRandomPartDev(config.averageMainSkill, 3)
        (st, tk, ps, sh)
      }
    }

    val aggression = RandomUtils.averagedRandomPartDev(config.averageAggression, 3)
    val stamina = RandomUtils.averagedRandomPartDev(config.averageStamina, 2)

    val stats = PlayerStats(st, tk, ps, sh, aggression, stamina, 300, 300, 300, 300)
    logger.debug(s"Roster.generatePlayerStats($config, $playerType) - Stats [$stats]")
    stats
  }

  /**
   * Chooses the preferred side of the player
   * @return the preferred side of the player
   */
  protected[sesms] def genPlayerPrefSide(): String = {
    val rand = RandomUtils.uniformRandom(150)

    val side = if (rand <= 8)
      "RLC"
    else if (rand <= 13)
      "RL"
    else if (rand <= 23)
      "RC"
    else if (rand <= 33)
      "LC"
    else if (rand <= 73)
      "R"
    else if (rand <= 103)
      "L"
    else
      "C"

    logger.debug(s"Roster.generatePlayerPrefSide() - rand: $rand - side: $side")
    side
  }

  /**
   * Port of the random name generator of ESMS v.2.7.3
   * @return a random name for a player
   */
  protected[sesms] def generateName(): String = {

    val vowelish = List("a", "o", "e", "i", "u")
    val vowelish_not_begin = List("ew", "ow", "oo", "oa", "oi", "oe", "ae", "ua")
    val consonantish = List("b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "y", "z", "br", "cl", "gr", "st", "jh", "tr", "ty", "dr", "kr", "ry", "bt", "sh", "ch", "pr")
    val consonantish_not_begin = List("mn", "nh", "rt", "rs", "rst", "dn", "nd", "ds", "bt", "bs", "bl", "sk", "vr", "ks", "sy", "ny", "vr", "sht", "ck")

    def nextLetterInName(vowel: Boolean, length: Int): String = if (length >= 0) {
      val letter = if (vowel) {
        Random.shuffle(consonantish ::: consonantish_not_begin).head
      } else {
        Random.shuffle(vowelish ::: vowelish_not_begin).head
      }

      letter + nextLetterInName(!vowel, length - 1)

    } else {
      ""
    }

    val randomLength = 2 + (3 * Random.nextGaussian()).toInt
    val nameLength = if (randomLength < 2) 2 else randomLength
    val cutLength = 9 + (3 * Random.nextGaussian()).round.toInt

    val name = Random.shuffle(vowelish ::: consonantish).head.head
    val genSurname = if (Random.nextBoolean()) {
      Random.shuffle(vowelish).head + nextLetterInName(true, nameLength)
    } else {
      Random.shuffle(consonantish).head + nextLetterInName(false, nameLength)
    }

    val surname = if (genSurname.size > 12) genSurname.substring(0, cutLength) else genSurname
    val playerName = name.toUpper + ". " + surname.head.toUpper + surname.tail
    logger.debug(s"Roster.generateName() - nameLength: $nameLength cutLength: $cutLength - name: $playerName")
    playerName
  }
}




