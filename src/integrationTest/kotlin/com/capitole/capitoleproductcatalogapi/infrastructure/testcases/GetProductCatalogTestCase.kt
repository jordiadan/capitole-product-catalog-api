package com.capitole.capitoleproductcatalogapi.infrastructure.testcases

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.charset.StandardCharsets

abstract class GetProductCatalogTestCase : TestCase() {

  private val mapper = ObjectMapper()

  @Test
  fun `should return product catalog with proper discounts applied for all products`() {
    val actualJson = given()
        .contentType("application/json")
        .get("/products?page=0&size=30")
        .then()
        .statusCode(200)
        .extract()
        .asString()

    val expectedTree = loadFixture("all_products_page0_size30.json")
    val actualTree = mapper.readTree(actualJson)

    assertEquals(expectedTree, actualTree)
  }

  // TODO: I should add a parameterized test to verify that can filter by any category
  @Test
  fun `should return only electronics items when filtering by category Electronics`() {
    val actualJson = given()
        .contentType("application/json")
        .get("/products?category=ELECTRONICS")
        .then()
        .statusCode(200)
        .extract()
        .asString()

    val expectedTree = loadFixture("electronics_default_page.json")
    val actualTree = mapper.readTree(actualJson)

    assertEquals(expectedTree, actualTree)
  }

  @ParameterizedTest(name = "{index} sort by {0} {1}")
  @CsvSource(
      "PRICE,ASC,7.50,499.00",
      "PRICE,DESC,499.00,7.50",
      "SKU,ASC,SKU0001,SKU0030",
      "SKU,DESC,SKU0030,SKU0001",
      "DESCRIPTION,ASC,'4K Ultra HD Smart TV, 55 inches','Yoga Mat with Non-Slip Surface'",
      "DESCRIPTION,DESC,'Yoga Mat with Non-Slip Surface','4K Ultra HD Smart TV, 55 inches'",
      "CATEGORY,ASC,Accessories,'Toys & Games'",
      "CATEGORY,DESC,'Toys & Games',Accessories"
  )
  fun `should return products sorted correctly`(
    sortField: String,
    sortOrder: String,
    first: String,
    last: String
  ) {
    given()
        .contentType("application/json")
        .get("/products?sortField=$sortField&sortOrder=$sortOrder&size=30")
        .then()
        .statusCode(200)
        .body("products", hasSize<Any>(30))
        .body("products[0].${sortField.lowercase()}", equalTo(first))
        .body("products[-1].${sortField.lowercase()}", equalTo(last))
  }

  @Test
  fun `should return first page with default size`() {
    val actual = given()
        .contentType("application/json")
        .get("/products")
        .then()
        .statusCode(200)
        .extract()
        .asString()

    val tree = mapper.readTree(actual)
    assertEquals(0, tree["page"].asInt())
    assertEquals(30, tree["totalElements"].asInt())
    assertEquals(2, tree["totalPages"].asInt())
    assertEquals(20, tree["size"].asInt())
    assertEquals(20, tree["products"].size())
  }

  @Test
  fun `should return second page with custom size`() {
    val actual = given()
        .contentType("application/json")
        .get("/products?page=1&size=5")
        .then()
        .statusCode(200)
        .extract()
        .asString()

    val tree = mapper.readTree(actual)
    assertEquals(1, tree["page"].asInt())
    assertEquals(5, tree["size"].asInt())
    assertEquals(30, tree["totalElements"].asInt())
    assertEquals(6, tree["totalPages"].asInt())
    assertEquals(5, tree["products"].size())
  }

  private fun loadFixture(path: String): JsonNode =
      javaClass.classLoader
          .getResourceAsStream("responses/$path")
          ?.bufferedReader(StandardCharsets.UTF_8)
          ?.use { mapper.readTree(it) }
        ?: error("Fixture not found: responses/$path")
}
