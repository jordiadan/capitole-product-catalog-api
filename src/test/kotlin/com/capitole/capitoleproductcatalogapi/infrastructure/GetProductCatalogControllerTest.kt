package com.capitole.capitoleproductcatalogapi.infrastructure

import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetProductCatalogControllerTest {

  private lateinit var mockMvc: MockMvc
  private lateinit var getProductCatalog: GetProductCatalog

  @BeforeEach
  fun setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(GetProductCatalogController()).build()
    getProductCatalog = mock()
  }

  @Test
  fun `should return product catalog with 200`() {
    val expectedJson = """
            [
              {
                "sku": "SKU0001",
                "description": "Wireless Mouse with ergonomic design",
                "price": "19.99",
                "discountPercentage": "0",
                "finalPrice": "19.99",
                "category": "Electronics"
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
        """.trimIndent()


    mockMvc.perform(get("/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().string(expectedJson))
  }
}
