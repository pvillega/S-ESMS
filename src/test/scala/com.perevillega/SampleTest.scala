package com.perevillega

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester

/**
 *
 * User: pvillega
 */
class SampleTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Default test") {

    it("should pass") {
      (1-1) should be (0)

    }

  }

}

