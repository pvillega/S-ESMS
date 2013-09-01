package com.perevillega.sesms.support

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester
import play.api.libs.json.Json

/**
 * Tests for Country
 * User: pvillega
 */
class CountryTest extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A Country object") {

    it("should return all countries") {
      val all = Country.getAllCountries()

      all.size should be(243)
      all(0).name should be("Afghanistan")
      all(0).code should be("AF")
      all(all.size-1).name should be("Zimbabwe")
      all(all.size-1).code should be("ZW")
    }

    it("should return random countries") {
      val all = Country.getAllCountries()
      val country = Country.getRandomCountry()
      val country2 = Country.getRandomCountry()
      val country3 = Country.getRandomCountry()

      all.contains(country) should be(true)
      all.contains(country2) should be(true)
      all.contains(country3) should be(true)

      country should not be(country2)
      country should not be(country3)
      country2 should not be(country3)
    }

    it("should load a Country from JSON") {
      val json = """{"name": "a", "code": "BB"}"""
      val country = Country.fromJson(Json.parse(json))

      country.name should be("a")
      country.code should be("BB")
    }

    it("should convert a Country to JSON") {
      val country = Country("a", "BB")
      val json = Country.toJson(country)

      (json \ "name").as[String] should be("a")
      (json \ "code").as[String] should be("BB")
    }

  }

  describe("A Country class") {

    // Empty

  }

}

