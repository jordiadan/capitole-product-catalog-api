package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.Description
import com.capitole.capitoleproductcatalogapi.domain.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.DiscountService
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
  private lateinit var discountService: DiscountService

  @BeforeEach
  fun setup() {
    productRepository = mock()
    discountService = mock()
    getProductCatalog = GetProductCatalog(productRepository, discountService)
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
    `when`(discountService.getApplicableDiscount(products[0])).thenReturn(DiscountPercentage(0.0))
    `when`(discountService.getApplicableDiscount(products[1])).thenReturn(DiscountPercentage(30.0))

    val result = getProductCatalog.execute()

    val expected = ProductCatalogDTO(
        products = listOf(
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

    assertEquals(expected, result)
  }

  @Test
  fun `execute should return product catalog filtered by Electronics category`() {
    val mouse = Product(
        sku = SKU("SKU0001"),
        description = Description("Wireless Mouse with ergonomic design"),
        price = Price(19.99),
        category = Category.ELECTRONICS
    )
    val mug = Product(
        sku = SKU("SKU0010"),
        description = Description("Ceramic Coffee Mug, 350ml"),
        price = Price(12.50),
        category = Category.HOME_AND_KITCHEN
    )

    `when`(productRepository.findAll()).thenReturn(listOf(mouse, mug))
    `when`(discountService.getApplicableDiscount(mouse)).thenReturn(DiscountPercentage(15.0))
    `when`(discountService.getApplicableDiscount(mug)).thenReturn(DiscountPercentage(25.0))

    val categoryFilter = Category.ELECTRONICS
    val result = getProductCatalog.execute(categoryFilter)

    val expected = ProductCatalogDTO(
        products = listOf(
            ProductCatalogDTO.ProductDTO(
                sku = "SKU0001",
                description = "Wireless Mouse with ergonomic design",
                price = PriceDTO("19.99"),
                discountPercentage = DiscountPercentageDTO("15.00"),
                finalPrice = "16.99",
                category = Category.ELECTRONICS.toDTO()
            )
        )
    )

    assertEquals(expected, result)
  }
}
