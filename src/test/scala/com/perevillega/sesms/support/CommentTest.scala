package com.perevillega.sesms.support

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import java.util.Locale

/**
 * Tests for Comment
 * User: pvillega
 */
class CommentTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Comment class") {

    it("should load 'en' Locale by default") {
      val comment = Comment()

      comment.locale should be(new Locale("en"))
      comment.locale.getLanguage should be("en")
      comment.locale.getCountry should be("")
    }

    it("should work for multi keys") {
      val comment = Comment()
      val options = List("M key A", "M key B", "M key C")

      options.contains(comment.get("multi.key")) should be(true)
    }

    it("should work for multi keys with params") {

      val comment = Comment()
      val values = List("A", 2, "J")
      val options = List("M key A with {0} and {1} and {2}".format(values),
        "M key B with {0} and {1} and {2}".format(values),
        "M key C with {0} and {1} and {2}".format(values))

      options.contains(comment.get("multi.key.params", values)) should be(true)
    }

    it("should work for single keys") {
      val comment = Comment()
      comment.get("single.key") should be("This is a single key")
    }

    it("should work for single keys with params") {
      val comment = Comment()
      val values = List("A", 2, "J")
      comment.get("single.key.params", values) should be("This is a single key with {0} and {1} and {2} params".format(values))
    }

  }

}

