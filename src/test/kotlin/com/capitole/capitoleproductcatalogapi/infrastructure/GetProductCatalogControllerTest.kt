package com.capitole.capitoleproductcatalogapi.infrastructure

import com.capitole.capitoleproductcatalogapi.application.DiscountPercentageDTO
import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.application.PriceDTO
import com.capitole.capitoleproductcatalogapi.application.ProductCatalogDTO
import com.capitole.capitoleproductcatalogapi.application.toDTO
import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.SortField
import com.capitole.capitoleproductcatalogapi.domain.SortOrder
import com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog.GetProductCatalogController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class GetProductCatalogControllerTest {

  private lateinit var mockMvc: MockMvc
  private lateinit var getProductCatalog: GetProductCatalog

  @BeforeEach
  fun setUp() {
    getProductCatalog = mock()
    mockMvc = MockMvcBuilders.standaloneSetup(GetProductCatalogController(getProductCatalog)).build()
  }

  @Test
  fun `should return product catalog with 200`() {
    val expectedJson = """
            {
              "products": [
                {
                  "sku": "SKU0001",
                  "description": "Wireless Mouse with ergonomic design",
                  "price": "19.99",
                  "discountPercentage": "0.00",
                  "finalPrice": "19.99",
                  "category": "Electronics"
                },
                {
                  "sku": "SKU0005",
                  "description": "Noise-Cancelling Over-Ear Headphones",
                  "price": "120.00",
                  "discountPercentage": "30.00",
                  "finalPrice": "84.00",
                  "category": "Electronics"
                }
              ]
            }
        """.trimIndent()
    `when`(getProductCatalog.execute(sortField = SortField.PRICE, sortOrder = SortOrder.ASC)).thenReturn(
        buildExpectedProductCatalogDTO()
    )

    mockMvc.perform(
        get("/products")
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk)
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson))
  }

  private fun buildExpectedProductCatalogDTO(): ProductCatalogDTO = ProductCatalogDTO(
      products = mutableListOf(
          ProductCatalogDTO.ProductDTO(
              sku = "SKU0001",
              description = "Wireless Mouse with ergonomic design",
              price = PriceDTO("19.99"),
              discountPercentage = DiscountPercentageDTO("0.00"),
              finalPrice = "19.99",
              category = Category.ELECTRONICS.toDTO()
          ),
          ProductCatalogDTO.ProductDTO(
              sku = "SKU0005",
              description = "Noise-Cancelling Over-Ear Headphones",
              price = PriceDTO("120.00"),
              discountPercentage = DiscountPercentageDTO("30.00"),
              finalPrice = "84.00",
              category = Category.ELECTRONICS.toDTO()
          )
      )
  )

  @Test
  fun `should return 400 when category is invalid`() {
    mockMvc.perform(
        get("/products?category=INVALID_CATEGORY")
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isBadRequest)
  }
}
