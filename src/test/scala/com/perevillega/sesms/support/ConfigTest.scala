package com.perevillega.sesms.support

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.Json

/**
 * Tests for Config
 * User: pvillega
 */
class ConfigTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Config object") {

    it("should load a Config from JSON") {
      val json = """{"map":{"a":"a","b":"b"}}"""
      val config = Config.fromJson(Json.parse(json))

      config.get("a") should be("a")
      config.get("b") should be("b")
    }

    it("should convert a Config to JSON") {
      val map = Map.empty[String, String] + ("a" -> "a") + ("b" -> "b")
      val config = Config(map)
      val json = Config.toJson(config)

      (json \ "map").as[Map[String, String]].size should be(2)
      (json \ "map").as[Map[String, String]].get("a") should be(Some("a"))
      (json \ "map").as[Map[String, String]].get("b") should be(Some("b"))
    }

  }

  describe("A Config class") {

    it("should return config stored as Int") {
      val map = Map.empty[String, String] + ("a" -> "1") + ("b" -> "2")
      val config = Config(map)

      config.getAsInt("a") should be(1)
      config.getAsInt("b") should be(2)

    }

    it("should return config stored as String") {
      val map = Map.empty[String, String] + ("a" -> "a") + ("b" -> "b")
      val config = Config(map)

      config.get("a") should be("a")
      config.get("b") should be("b")
    }

  }

}

