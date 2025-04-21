package com.capitole.capitoleproductcatalogapi.infrastructure.testcases

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

abstract class GetProductCatalogTestCase : TestCase() {

  private val mapper = ObjectMapper()

  // TODO: Improve the readability of the test, move the json to a file
  @Test
  fun `should return product catalog with proper discounts applied for all products`() {
    val expectedJson = """
    {
      "products": [
        {"sku":"SKU0001","description":"Wireless Mouse with ergonomic design","price":"19.99","discountPercentage":"15.00","finalPrice":"16.99","category":"Electronics"},
        {"sku":"SKU0002","description":"4K Ultra HD Smart TV, 55 inches","price":"499.00","discountPercentage":"15.00","finalPrice":"424.15","category":"Electronics"},
        {"sku":"SKU0003","description":"Stainless Steel Water Bottle, 1L","price":"29.50","discountPercentage":"25.00","finalPrice":"22.13","category":"Home & Kitchen"},
        {"sku":"SKU0004","description":"Cotton T-Shirt, Unisex, Size M","price":"15.00","discountPercentage":"0.00","finalPrice":"15.00","category":"Clothing"},
        {"sku":"SKU0005","description":"Noise-Cancelling Over-Ear Headphones","price":"120.00","discountPercentage":"30.00","finalPrice":"84.00","category":"Electronics"},
        {"sku":"SKU0006","description":"USB-C to USB Adapter","price":"9.99","discountPercentage":"15.00","finalPrice":"8.49","category":"Electronics"},
        {"sku":"SKU0007","description":"Leather Wallet with RFID Protection","price":"75.00","discountPercentage":"0.00","finalPrice":"75.00","category":"Accessories"},
        {"sku":"SKU0008","description":"Yoga Mat with Non-Slip Surface","price":"35.00","discountPercentage":"0.00","finalPrice":"35.00","category":"Sports"},
        {"sku":"SKU0009","description":"Smartwatch with Heart Rate Monitor","price":"220.00","discountPercentage":"15.00","finalPrice":"187.00","category":"Electronics"},
        {"sku":"SKU0010","description":"Ceramic Coffee Mug, 350ml","price":"12.50","discountPercentage":"25.00","finalPrice":"9.38","category":"Home & Kitchen"},
        {"sku":"SKU0011","description":"Bluetooth Portable Speaker","price":"60.00","discountPercentage":"15.00","finalPrice":"51.00","category":"Electronics"},
        {"sku":"SKU0012","description":"Backpack with Laptop Compartment","price":"85.00","discountPercentage":"0.00","finalPrice":"85.00","category":"Accessories"},
        {"sku":"SKU0013","description":"Stainless Steel Cutlery Set, 24 Pieces","price":"18.00","discountPercentage":"25.00","finalPrice":"13.50","category":"Home & Kitchen"},
        {"sku":"SKU0014","description":"Electric Guitar Starter Pack","price":"250.00","discountPercentage":"0.00","finalPrice":"250.00","category":"Musical Instruments"},
        {"sku":"SKU0015","description":"Running Shoes, Men's Size 42","price":"42.00","discountPercentage":"30.00","finalPrice":"29.40","category":"Footwear"},
        {"sku":"SKU0016","description":"Digital Bathroom Scale with Body Fat Analyzer","price":"27.99","discountPercentage":"0.00","finalPrice":"27.99","category":"Home Appliances"},
        {"sku":"SKU0017","description":"Set of 6 Organic Cotton Socks","price":"14.99","discountPercentage":"0.00","finalPrice":"14.99","category":"Clothing"},
        {"sku":"SKU0018","description":"DSLR Camera with 18-55mm Lens","price":"300.00","discountPercentage":"15.00","finalPrice":"255.00","category":"Electronics"},
        {"sku":"SKU0019","description":"Hardcover Notebook, A5, 200 Pages","price":"8.99","discountPercentage":"0.00","finalPrice":"8.99","category":"Stationery"},
        {"sku":"SKU0020","description":"Microwave Oven, 20L Capacity","price":"65.00","discountPercentage":"0.00","finalPrice":"65.00","category":"Home Appliances"},
        {"sku":"SKU0021","description":"LED Desk Lamp with Adjustable Brightness","price":"23.50","discountPercentage":"25.00","finalPrice":"17.63","category":"Home & Kitchen"},
        {"sku":"SKU0022","description":"Wireless Charger Pad for Smartphones","price":"19.00","discountPercentage":"15.00","finalPrice":"16.15","category":"Electronics"},
        {"sku":"SKU0023","description":"Men's Quartz Analog Watch with Leather Strap","price":"55.00","discountPercentage":"0.00","finalPrice":"55.00","category":"Accessories"},
        {"sku":"SKU0024","description":"Wooden Chess Set with Folding Board","price":"30.00","discountPercentage":"0.00","finalPrice":"30.00","category":"Toys & Games"},
        {"sku":"SKU0025","description":"Home Security Camera with Night Vision","price":"99.00","discountPercentage":"30.00","finalPrice":"69.30","category":"Electronics"},
        {"sku":"SKU0026","description":"Aromatherapy Essential Oil Diffuser","price":"16.50","discountPercentage":"25.00","finalPrice":"12.38","category":"Home & Kitchen"},
        {"sku":"SKU0027","description":"Professional Blender with 2L Jar","price":"40.00","discountPercentage":"0.00","finalPrice":"40.00","category":"Home Appliances"},
        {"sku":"SKU0028","description":"Kids' Educational Tablet Toy","price":"22.00","discountPercentage":"0.00","finalPrice":"22.00","category":"Toys & Games"},
        {"sku":"SKU0029","description":"Mechanical Gaming Keyboard with RGB Lighting","price":"110.00","discountPercentage":"15.00","finalPrice":"93.50","category":"Electronics"},
        {"sku":"SKU0030","description":"Pack of 10 Ballpoint Pens, Blue Ink","price":"7.50","discountPercentage":"0.00","finalPrice":"7.50","category":"Stationery"}
      ]
    }
    """.trimIndent()

    val actualJson = given()
        .contentType("application/json")
        .get("/products?page=0&size=30")
        .then()
        .statusCode(200)
        .extract()
        .asString()

    val expectedTree = mapper.readTree(expectedJson)
    val actualTree = mapper.readTree(actualJson)

    assertEquals(expectedTree, actualTree)
  }

  // TODO: I should add a parameterized test to verify that can filter by any category
  @Test
  fun `should return only electronics items when filtering by category Electronics`() {
    val expectedJson = """
    {
      "products": [
        {"sku":"SKU0001","description":"Wireless Mouse with ergonomic design","price":"19.99","discountPercentage":"15.00","finalPrice":"16.99","category":"Electronics"},
        {"sku":"SKU0002","description":"4K Ultra HD Smart TV, 55 inches","price":"499.00","discountPercentage":"15.00","finalPrice":"424.15","category":"Electronics"},
        {"sku":"SKU0005","description":"Noise-Cancelling Over-Ear Headphones","price":"120.00","discountPercentage":"30.00","finalPrice":"84.00","category":"Electronics"},
        {"sku":"SKU0006","description":"USB-C to USB Adapter","price":"9.99","discountPercentage":"15.00","finalPrice":"8.49","category":"Electronics"},
        {"sku":"SKU0009","description":"Smartwatch with Heart Rate Monitor","price":"220.00","discountPercentage":"15.00","finalPrice":"187.00","category":"Electronics"},
        {"sku":"SKU0011","description":"Bluetooth Portable Speaker","price":"60.00","discountPercentage":"15.00","finalPrice":"51.00","category":"Electronics"},
        {"sku":"SKU0018","description":"DSLR Camera with 18-55mm Lens","price":"300.00","discountPercentage":"15.00","finalPrice":"255.00","category":"Electronics"},
        {"sku":"SKU0022","description":"Wireless Charger Pad for Smartphones","price":"19.00","discountPercentage":"15.00","finalPrice":"16.15","category":"Electronics"},
        {"sku":"SKU0025","description":"Home Security Camera with Night Vision","price":"99.00","discountPercentage":"30.00","finalPrice":"69.30","category":"Electronics"},
        {"sku":"SKU0029","description":"Mechanical Gaming Keyboard with RGB Lighting","price":"110.00","discountPercentage":"15.00","finalPrice":"93.50","category":"Electronics"}
      ]
    }
    """.trimIndent()

    val actualJson = given()
        .contentType("application/json")
        .get("/products?category=ELECTRONICS")
        .then()
        .statusCode(200)
        .extract()
        .asString()

    val expectedTree = mapper.readTree(expectedJson)
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
    assertEquals(6, tree["totalPages"].asInt())
    assertEquals(10, tree["size"].asInt())
    assertEquals(10, tree["content"].size())
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
    assertEquals(5, tree["content"].size())
  }
}
