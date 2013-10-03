package com.perevillega.sesms.support

import scala.util.Random
import org.slf4j.LoggerFactory

/**
 * Support methods for random value generation as per ESMS v2.7.3
 * User: pvillega
 */
object RandomUtils {
  private val logger = LoggerFactory.getLogger(this.getClass)

  /**
   * Added to follow the same logic as ESMS v.2.7.3
   * Returns a random value between 0 and max - 1 (both included)
   * @param max maximum value of the randomization
   * @return a random value between 0 and max - 1 (both included)
   */
  def uniformRandom(max: Int): Int = Random.nextInt(max)

  /**
   * Added to follow the same logic as ESMS v.2.7.3
   * Returns a random value in the range average + 0 to (average/dev)*gaussFactor
   * It's used to generate values in a range proportional to the average
   * The calculation uses a gaussian normal distribution for the probability
   * @param average average value in the range
   * @param dev partial deviation allowed in the range
   * @return a random value in the range average + 0 to (average/dev)*gaussFactor
   */
  def averagedRandomPartDev(average: Int, dev: Int): Int = averagedRandom(average, average / dev)

  /**
   * Added to follow the same logic as ESMS v.2.7.3
   * Returns a random value in the range average + (0 to maxDev*gaussFactor)
   * The calculation uses a gaussian normal distribution for the probability
   * @param average average value in the range
   * @param maxDev deviation allowed in the range
   * @return a random value in the range average + (0 to maxDev*gaussFactor)
   */
  def averagedRandom(average: Int, maxDev: Int): Int = {
    val gaussian = Random.nextGaussian()
    val dev = (maxDev * gaussian).abs.floor.toInt
    val result = average + dev
    logger.trace(s"RandomUtils.averagedRandom($average, $maxDev) - Gauss: $gaussian - Dev: $dev - Result [$result]")
    result
  }

}
