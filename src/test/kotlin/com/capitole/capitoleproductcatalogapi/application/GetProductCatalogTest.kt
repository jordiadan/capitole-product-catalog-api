package com.capitole.capitoleproductcatalogapi.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetProductCatalogTest {

  private lateinit var getProductCatalog: GetProductCatalog

  @BeforeEach
  fun setup() {
    getProductCatalog = GetProductCatalog()
  }

   @Test
   fun `execute returns expected product catalog`() {
     val result = getProductCatalog.execute()

     val expected = ProductCatalogDTO(
         products = listOf(
             ProductCatalogDTO.ProductDTO(
                 sku = "SKU0001",
                 description = "Wireless Mouse with ergonomic design",
                 price = "19.99",
                 discountPercentage = "0",
                 finalPrice = "19.99",
                 category = "Electronics"
             ),
             ProductCatalogDTO.ProductDTO(
                 sku = "SKU0005",
                 description = "Noise-Cancelling Over-Ear Headphones",
                 price = "120.00",
                 discountPercentage = "30",
                 finalPrice = "84.00",
                 category = "Electronics"
             )
         )
     )

     assertEquals(expected, result)
   }
 }
