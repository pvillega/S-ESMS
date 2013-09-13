package com.perevillega.sesms

import org.slf4j.LoggerFactory
import play.api.libs.json.{JsValue, Json}


/**
 * Represents the result of a match
 * @param homeTeam name of the home team
 * @param awayTeam name of the away team
 * @param goalsHome goals scored by home team
 * @param goalsAway goals scored by away team
 */
case class MatchResult(homeTeam: String, awayTeam: String, goalsHome: Int, goalsAway: Int)  {

  val isHomeWinner = goalsHome > goalsAway
  val isAwayWinner = goalsHome < goalsAway
  val isDraw = goalsHome == goalsAway
}


object MatchResult {
  /**
   * Support object to convert MatchResult to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[MatchResult]

  /**
   * Constructs a MatchResult from its Json version
   * @param json Json value that contains a MatchResult
   * @return a MatchResult object built from the json parameter
   */
  def fromJson(json: JsValue): MatchResult = Json.fromJson[MatchResult](json).get

  /**
   * Creates a json version of the MatchResult
   * @param matchResult the MatchResult object to convert to json
   * @return a JsValue with the MatchResult
   */
  def toJson(matchResult: MatchResult): JsValue = Json.toJson(matchResult)
}

/**
 * A row in the table league
 * @param team name of the team
 * @param win number of games won
 * @param draw number of games drawn
 * @param lost number of games lost
 * @param goalsScored number of goals scored
 * @param goalsReceived number of goals received
 */
case class TableEntry(team: String, win: Int, draw: Int, lost: Int, goalsScored: Int, goalsReceived: Int) {
  val gamesPlayed = win + draw + lost

  val goalsDifference = goalsScored - goalsReceived

  val points = 3*win + draw
}


object TableEntry {
  /**
   * Support object to convert TableEntry to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[TableEntry]

  /**
   * Constructs a TableEntry from its Json version
   * @param json Json value that contains a TableEntry
   * @return a TableEntry object built from the json parameter
   */
  def fromJson(json: JsValue): TableEntry = Json.fromJson[TableEntry](json).get

  /**
   * Creates a json version of the TableEntry
   * @param tableEntry the TableEntry object to convert to json
   * @return a JsValue with the TableEntry
   */
  def toJson(tableEntry: TableEntry): JsValue = Json.toJson(tableEntry)
}


/**
 * Represents the classification of the league
 * @param table the initial table of the league indexed by team name
 * User: pvillega
 */
case class LeagueTable(table: Map[String, TableEntry]) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  /**
   * Creates a new LeagueTable that contains the given team with a blank slate
   * If the team already exists, it removes its existing values as per ESMS 2.7.3
   * @param name name of the team
   * @return a new LeagueTable that contains the given team with a blank slate
   */
  def addTeam(name: String): LeagueTable = {
    logger.info(s"LeagueTable.addTeam($name) - team exists? ${table.contains(name)}")
    val tableEntry = TableEntry(name, 0, 0, 0, 0, 0)
    val newTable = table -(name) +(name -> tableEntry)
    val league = LeagueTable(newTable)
    logger.info(s"LeagueTable.addTeam($name) - Results [$league]")
    league
  }

  /**
   * Updates the league table with the given results
   * @param results list of results to update the table
   * @return a new league table with the results updated
   */
  def updateTable(results: List[MatchResult]): LeagueTable = {
    logger.info(s"LeagueTable.updateTable($results) - Enter")

    val newTable = results.flatMap { mr =>
      val homeTeam = table(mr.homeTeam)
      val awayTeam = table(mr.awayTeam)

      val homeWins =  if(mr.isHomeWinner) homeTeam.win + 1 else homeTeam.win
      val homeDraw =  if(mr.isDraw) homeTeam.draw + 1 else homeTeam.draw
      val homeLost =  if(mr.isAwayWinner) homeTeam.lost + 1 else homeTeam.lost
      val homeScored = homeTeam.goalsScored + mr.goalsHome
      val homeReceived = homeTeam.goalsReceived + mr.goalsAway


      val awayWins =  if(mr.isAwayWinner) awayTeam.win + 1 else awayTeam.win
      val awayDraw =  if(mr.isDraw) awayTeam.draw + 1 else awayTeam.draw
      val awayLost =  if(mr.isHomeWinner) awayTeam.lost + 1 else awayTeam.lost
      val awayScored = awayTeam.goalsScored + mr.goalsAway
      val awayReceived = awayTeam.goalsReceived + mr.goalsHome

      List( (homeTeam.team -> TableEntry(homeTeam.team, homeWins, homeDraw, homeLost, homeScored, homeReceived)),
        (awayTeam.team -> TableEntry(awayTeam.team, awayWins, awayDraw, awayLost, awayScored, awayReceived)) )
    }.toMap

    val league = LeagueTable(newTable)
    logger.info(s"LeagueTable.updateTable($results) - Results [$league]")
    league
  }
}


/**
 * Updates a league table
 * User: pvillega
 */
object LeagueTable {

  /**
   * Support object to convert LeagueTable to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[LeagueTable]

  /**
   * Constructs a LeagueTable from its Json version
   * @param json Json value that contains a LeagueTable
   * @return a LeagueTable object built from the json parameter
   */
  def fromJson(json: JsValue): LeagueTable = Json.fromJson[LeagueTable](json).get

  /**
   * Creates a json version of the LeagueTable
   * @param leagueTable the LeagueTable object to convert to json
   * @return a JsValue with the LeagueTable
   */
  def toJson(leagueTable: LeagueTable): JsValue = Json.toJson(leagueTable)
}