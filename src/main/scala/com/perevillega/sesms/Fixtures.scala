package com.perevillega.sesms

import org.slf4j.LoggerFactory
import play.api.libs.json.{JsValue, Json}
import scala.util.Random


/**
 * Contains all games in a given week
 * @param week week of the season the games are played
 * @param games list of games, pair Home vs Visitant
 */
case class Fixtures(week: Int, games: List[List[String]])

/**
 * Generates fixtures for a list of teams
 * User: pvillega
 */
object Fixtures {
  private val logger = LoggerFactory.getLogger(this.getClass)

  val placeholder = "(DAY OFF)"

  /**
   * Support object to convert Fixtures to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[Fixtures]

  /**
   * Constructs a Fixtures from its Json version
   * @param json Json value that contains a Fixtures
   * @return a Fixtures object built from the json parameter
   */
  def fromJson(json: JsValue): Fixtures = Json.fromJson[Fixtures](json).get

  /**
   * Creates a json version of the Fixtures
   * @param fixtures the Fixtures object to convert to json
   * @return a JsValue with the Fixtures
   */
  def toJson(fixtures: Fixtures): JsValue = Json.toJson(fixtures)

  /**
   * Given a list of teams creates a list of fixtures to be played corresponding to a league
   * @param teams teams that belong to the league
   * @return list of games of the season ordered per week
   */
  def createFixtures(teams: List[String]) : List[Fixtures] = {
    logger.info(s"Fixtures.createFixtures($teams)")


    /**
     * Creates a Tuple that maps a game played in a given round
     * @param round round (week) of the game
     * @param game number mapping a team
     * @param numberOfTeams total number of teams
     * @return a Tuple3 with the week, position of home team and position of away team
     */
    def createTuple(round: Int, game: Int, numberOfTeams: Int) = {
      val home = if(game == 0) numberOfTeams - 1 else (game + round) % (numberOfTeams - 1)
      val away = (numberOfTeams - 1 - game + round) % (numberOfTeams - 1)

      (round, home, away)
    }

    /**
     * Fix the home/away issue on games
     * Works better the more teams are in the fixtures
     * @param lastWeek last week's Fixtures
     * @param list remaining fixtures to fix
     */
    def fixHomeAwayGames(lastWeek: Fixtures, list: List[Fixtures]): List[Fixtures] = list match {
      case Nil => lastWeek :: Nil
      case _ =>  {
        val head = list.head

        //fix fixtures in list head
        val games = head.games.map { case List(a,b) =>
          val wasHome = lastWeek.games.exists( l => l(0) != placeholder && l(0) == a )
          if(wasHome) List(b, a) else List(a, b)
        }

        //fix remainder of list
        lastWeek :: fixHomeAwayGames( head.copy(games = games), list.tail)
      }
    }


    logger.debug("Make teams even if they are not and shuffle list to avoid predictable ordering")
    val evenTeams = if(teams.size % 2 == 0) teams else placeholder :: teams
    val randomized = Random.shuffle(evenTeams)

    logger.debug("Create a list of indexes with all the possible permutations")
    val numberOfTeams = randomized.length
    val listing = for {
      round <- 1 to numberOfTeams - 1
      game <- 0 to numberOfTeams/2 - 1
    } yield createTuple(round, game, numberOfTeams)

    logger.debug("Group by week and fix home-away into Fixtures")
    val listFixtures = listing.groupBy(_._1).map { case (k, list) =>
      logger.debug("Map indexes to names")
      val games = list.toList.map{ case (a,b,c) => List(randomized(b), randomized(c)) }
      Fixtures(k, games)
    }

    logger.debug("Sort Fixtures by week and order home/away games")
    val sortedFixtures = listFixtures.toList.sortBy( f => f.week)
    //only order if we have enough games
    val fixtures = if(sortedFixtures.length > 1) fixHomeAwayGames( sortedFixtures.head, sortedFixtures.tail) else sortedFixtures

    logger.info(s"Fixtures.createFixtures($teams) - Result: [$fixtures]")
    fixtures
  }
}
