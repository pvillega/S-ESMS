package com.perevillega.sesms.support

import play.api.libs.json.{JsValue, Json}

/**
 * Configuration holder
 * User: pvillega
 */
case class Config(val map: Map[String, String]) {

  /**
   * Gets a property
   * @param name property name
   * @return a property value
   */
  def get(name: String): String = map.getOrElse(name, "")

  /**
   * Gets a property as Int
   * @param name property name
   * @return a property value as Int
   */
  def getAsInt(name: String): Int = map.getOrElse(name, "0").toInt

}

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



object Config {
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
}

object RosterConfig {
  /**
   * Support object to convert RosterConfig to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[RosterConfig]

  /**
   * Constructs a RosterConfig from its Json version
   * @param json Json value that contains a RosterConfig
   * @return a RosterConfig object built from the json parameter
   */
  def fromJson(json: JsValue): RosterConfig = Json.fromJson[RosterConfig](json).get

  /**
   * Creates a json version of the RosterConfig
   * @param rosterConfig RosterConfig to convert to JSON
   * @return a JsValue with the RosterConfig
   */
  def toJson(rosterConfig: RosterConfig): JsValue = Json.toJson(rosterConfig)

  /**
   * Returns the default RosterConfig used for Roster creation as per ESMS 2.7.3
   * @return default RosterConfig used for Roster creation as per ESMS 2.7.3
   */
  def getDefaultRosterConfig(): RosterConfig = RosterConfig(3, 8, 3, 8, 3, 5, 60, 30, 14, 11, 7)
}
