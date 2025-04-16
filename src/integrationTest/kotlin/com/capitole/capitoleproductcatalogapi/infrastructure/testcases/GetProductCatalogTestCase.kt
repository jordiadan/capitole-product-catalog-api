package com.capitole.capitoleproductcatalogapi.infrastructure.testcases

import com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog.GetProductCatalogResponse
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

abstract class GetProductCatalogTestCase : TestCase() {

    @Test
    fun `should return product catalog with proper discounts applied`() {
        given()
            .contentType("application/json")
            .get("/products")
            .then()
            .statusCode(200)
            .body("products", hasSize<Int>(2))
            .body("products[0].sku", equalTo("SKU0001"))
            .body("products[0].description", equalTo("Wireless Mouse with ergonomic design"))
            .body("products[0].price", equalTo("19.99"))
            .body("products[0].discountPercentage", equalTo("0"))
            .body("products[0].finalPrice", equalTo("19.99"))
            .body("products[0].category", equalTo("Electronics"))
            .body("products[1].sku", equalTo("SKU0005"))
            .body("products[1].description", equalTo("Noise-Cancelling Over-Ear Headphones"))
            .body("products[1].price", equalTo("120.00"))
            .body("products[1].discountPercentage", equalTo("30"))
            .body("products[1].finalPrice", equalTo("84.00"))
            .body("products[1].category", equalTo("Electronics"))    }
}
