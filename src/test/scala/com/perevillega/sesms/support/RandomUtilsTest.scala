package com.perevillega.sesms.support

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.Json

/**
 * Tests for RandomUtils
 * User: pvillega
 */
class RandomUtilsTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A RandomUtils object") {

    it("should run uniformRandom") {
      val range = (1 to 10000).toList
      val randoms = range.map(RandomUtils.uniformRandom(_))

      val list = range zip randoms
      list.foreach( t => t._1 should be >(t._2))
    }

    it("should run averagedRandomPartDev") {
      val range = (5 to 10000).toList
      val randoms_1 = range.map(RandomUtils.averagedRandomPartDev(_, 1))
      val randoms_2 = range.map(RandomUtils.averagedRandomPartDev(_, 2))
      val randoms_3 = range.map(RandomUtils.averagedRandomPartDev(_, 3))

      val list1 = range zip randoms_1
      val list2 = range zip randoms_2
      val list3 = range zip randoms_3

      list1.foreach( t => t._2 should be (t._1 plusOrMinus 5*(t._2)) )
      list2.foreach( t => t._2 should be (t._1 plusOrMinus 5*(t._2/2)) )
      list3.foreach( t => t._2 should be (t._1 plusOrMinus 5*(t._2/3)) )

      list1.foreach( t => t._2 should be >= (0) )
      list2.foreach( t => t._2 should be >= (0) )
      list3.foreach( t => t._2 should be >= (0) )
    }

    it("should run averagedRandom") {
      val range = (1 to 10000).toList
      val randoms_1 = range.map(RandomUtils.averagedRandom(_, 1))
      val randoms_2 = range.map(RandomUtils.averagedRandom(_, 2))
      val randoms_3 = range.map(RandomUtils.averagedRandom(_, 3))

      val list1 = range zip randoms_1
      val list2 = range zip randoms_2
      val list3 = range zip randoms_3

      list1.foreach( t => t._2 should be (t._1 plusOrMinus 5*1))
      list2.foreach( t => t._2 should be (t._1 plusOrMinus 5*2))
      list3.foreach( t => t._2 should be (t._1 plusOrMinus 5*3))

      list1.foreach( t => t._2 should be >= (0))
      list2.foreach( t => t._2 should be >= (0))
      list3.foreach( t => t._2 should be >= (0))
    }

  }

}

