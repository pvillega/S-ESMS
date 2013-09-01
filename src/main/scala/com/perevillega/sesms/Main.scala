package com.perevillega.sesms

import com.perevillega.sesms.support.RosterConfig

/**
 * Support main object, to see output of some methods in terminal
 * User: pvillega
 */
object Main  extends App {

  // generate and print a standard roster
  val roster = Roster.createRoster(RosterConfig.getDefaultRosterConfig(), "MyTeam")
  roster.players.foreach(println(_))

}
