package com.capitole.capitoleproductcatalogapi.infrastructure.testcases

import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test

abstract class GetProductCatalogTestCase : TestCase() {

    @Test
    fun `should return product catalog with proper discounts applied`() {


      val expectedJson = """
            {
              "products": [
                {
                  "sku": "SKU0001",
                  "description": "Wireless Mouse with ergonomic design",
                  "price": "19.99",
                  "discountPercentage": "0",
                  "finalPrice": "19.99",
                  "category": "Electronics"
                },
                {
                  "sku": "SKU0002",
                  "description": "4K Ultra HD Smart TV, 55 inches",
                  "price": "499.00",
                  "discountPercentage": "15",
                  "finalPrice": "424.15",
                  "category": "Electronics"
                },
                {
                  "sku": "SKU0003",
                  "description": "Stainless Steel Water Bottle, 1L",
                  "price": "29.50",
                  "discountPercentage": "25",
                  "finalPrice": "22.13",
                  "category": "Home & Kitchen"
                },
                {
                  "sku": "SKU0005",
                  "description": "Noise-Cancelling Over-Ear Headphones",
                  "price": "120.00",
                  "discountPercentage": "30",
                  "finalPrice": "84.00",
                  "category": "Electronics"
                }
              ]
            }
        """.trimIndent()

        given()
            .contentType("application/json")
            .get("/products")
            .then()
            .statusCode(200)
            .body(equalTo(expectedJson))
    }
}
