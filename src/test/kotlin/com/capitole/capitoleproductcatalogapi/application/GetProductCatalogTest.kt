package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.PriceDTO
import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto.DiscountPercentageDTO
import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto.ProductCatalogDTO
import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto.toDTO
import com.capitole.capitoleproductcatalogapi.domain.pagination.Page
import com.capitole.capitoleproductcatalogapi.domain.pagination.PageRequest
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortOrder
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec
import org.junit.jupiter.api.Assertions.assertEquals
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
    val paginatedProducts = Page(
        content = products,
        pageNumber = 0,
        pageSize = 2,
        totalElements = 2
    )

    `when`(
        productRepository.findAll(
            sort = SortSpec(SortField.PRICE, SortOrder.ASC),
            pageRequest = PageRequest.of(0, 2)
        )
    ).thenReturn(paginatedProducts)
    `when`(discountService.getApplicableDiscount(products[0])).thenReturn(DiscountPercentage(0.0))
    `when`(discountService.getApplicableDiscount(products[1])).thenReturn(DiscountPercentage(30.0))

    val result = getProductCatalog.execute(
        sortField = SortField.PRICE,
        sortOrder = SortOrder.ASC,
        pageRequest = PageRequest.of(0, 2)
    )

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
        ),
        page = 0,
        size = 2,
        totalElements = 2,
        totalPages = 1
    )

    assertEquals(expected, result)
  }
}
