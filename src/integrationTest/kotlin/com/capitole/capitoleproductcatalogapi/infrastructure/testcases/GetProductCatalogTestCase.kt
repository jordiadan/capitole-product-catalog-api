package com.capitole.capitoleproductcatalogapi.infrastructure.testcases

import com.fasterxml.jackson.databind.JsonNode
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

abstract class GetProductCatalogTestCase : TestCase() {

  @Test
  fun `should return product catalog with proper discounts applied for all products`() {
    val expected = loadFixture("all_products_page0_size30.json")
    val actual = fetchTree("/products?page=0&size=30")
    assertEquals(expected, actual)
  }

  @Test
  fun `should return only electronics items when filtering by category Electronics`() {
    val expected = loadFixture("electronics_default_page.json")
    val actual = fetchTree("/products?category=ELECTRONICS")
    assertEquals(expected, actual)
  }

  @ParameterizedTest(name = "`[{index}] sort by {0} {1}`")
  @CsvSource(
      "PRICE,ASC,         7.50,499.00",
      "PRICE,DESC,        499.00,7.50",
      "SKU,ASC,           SKU0001,SKU0030",
      "SKU,DESC,          SKU0030,SKU0001",
      "DESCRIPTION,ASC,   '4K Ultra HD Smart TV, 55 inches','Yoga Mat with Non-Slip Surface'",
      "DESCRIPTION,DESC,  'Yoga Mat with Non-Slip Surface','4K Ultra HD Smart TV, 55 inches'",
      "CATEGORY,ASC,      Accessories,'Toys & Games'",
      "CATEGORY,DESC,     'Toys & Games',Accessories"
  )
  fun `should return products sorted correctly`(
    sortField: String,
    sortOrder: String,
    first: String,
    last: String
  ) {
    val uri = "/products?sortField=$sortField&sortOrder=$sortOrder&size=30"
    val tree = fetchTree(uri, expectedSize = 30)
    assertEquals(first, tree["products"][0][sortField.lowercase()].asText())
    assertEquals(last, tree["products"].last()[sortField.lowercase()].asText())
  }

  @Test
  fun `should return first page with default size`() {
    val tree = fetchTree("/products")
    assertEquals(0, tree["page"].asInt())
    assertEquals(30, tree["totalElements"].asInt())
    assertEquals(2, tree["totalPages"].asInt())
    assertEquals(20, tree["size"].asInt())
    assertEquals(20, tree["products"].size())
  }

  @Test
  fun `should return second page with custom size`() {
    val tree = fetchTree("/products?page=1&size=5")
    assertEquals(1, tree["page"].asInt())
    assertEquals(5, tree["size"].asInt())
    assertEquals(30, tree["totalElements"].asInt())
    assertEquals(6, tree["totalPages"].asInt())
    assertEquals(5, tree["products"].size())
  }

  private fun fetchTree(uri: String, expectedSize: Int? = null): JsonNode {
    val response = given()
        .contentType("application/json")
        .get(uri)
        .then()
        .statusCode(200)
        .apply { expectedSize?.let { body("products", hasSize<Any>(it)) } }
        .extract()
        .asString()
    return mapper.readTree(response)
  }
}
