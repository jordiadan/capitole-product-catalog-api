package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.Description
import com.capitole.capitoleproductcatalogapi.domain.Price
import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.SKU
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetProductCatalogTest {

  private lateinit var getProductCatalog: GetProductCatalog
  private lateinit var productRepository: ProductRepository

  @BeforeEach
  fun setup() {
    productRepository = mock()
    getProductCatalog = GetProductCatalog(productRepository)
  }

  @Test
  fun `execute returns expected product catalog`() {

    val products = listOf(
        Product(
            sku = SKU("SKU0001"),
            description = Description("Wireless Mouse with ergonomic design"),
            price = Price(19.99),
            category = Category.ELECTRONICS
        ),
        Product(
            sku = SKU("SKU0005"),
            description = Description("Noise-Cancelling Over-Ear Headphones"),
            price = Price(120.00),
            category = Category.ELECTRONICS
        )
    )

    `when`(productRepository.findAll()).thenReturn(products)

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
