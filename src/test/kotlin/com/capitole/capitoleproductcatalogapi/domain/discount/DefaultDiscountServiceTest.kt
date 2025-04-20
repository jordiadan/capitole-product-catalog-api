package com.capitole.capitoleproductcatalogapi.domain.discount

import com.capitole.capitoleproductcatalogapi.domain.discount.rules.CategoryRule
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.SkuEndsWith5Rule
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultDiscountServiceTest {
  private lateinit var discountService: DefaultDiscountService

  @BeforeEach
  fun setUp() {
    val rules = listOf(
        SkuEndsWith5Rule(),
        CategoryRule(Category.HOME_AND_KITCHEN, 25.0),
        CategoryRule(Category.ELECTRONICS, 15.0)
    )
    discountService = DefaultDiscountService(rules)
  }

  @Test
  fun `sku ending with 5 gets 30 percent discount`() {
    val product = Product(
        sku = SKU("SKU0005"),
        description = Description("Any"),
        price = Price(100.0),
        category = Category.ELECTRONICS
    )
    val result = discountService.getApplicableDiscount(product)
    assertEquals(DiscountPercentage(30.0), result)
  }

  @Test
  fun `electronics category gets 15 percent discount`() {
    val product = Product(
        sku = SKU("SKU0001"),
        description = Description("Any"),
        price = Price(100.0),
        category = Category.ELECTRONICS
    )
    val result = discountService.getApplicableDiscount(product)
    assertEquals(DiscountPercentage(15.0), result)
  }

  @Test
  fun `home and kitchen category gets 25 percent discount`() {
    val product = Product(
        sku = SKU("SKU0001"),
        description = Description("Any"),
        price = Price(100.0),
        category = Category.HOME_AND_KITCHEN
    )
    val result = discountService.getApplicableDiscount(product)
    assertEquals(DiscountPercentage(25.0), result)
  }

  @Test
  fun `other categories get zero discount`() {
    val product = Product(
        sku = SKU("SKU0001"),
        description = Description("Any"),
        price = Price(100.0),
        category = Category.CLOTHING
    )
    val result = discountService.getApplicableDiscount(product)
    assertEquals(DiscountPercentage(0.0), result)
  }

  @Test
  fun `sku ending with 5 overrides category discount`() {
    val product = Product(
        sku = SKU("SKU0005"),
        description = Description("Any"),
        price = Price(100.0),
        category = Category.HOME_AND_KITCHEN
    )
    val result = discountService.getApplicableDiscount(product)
    assertEquals(DiscountPercentage(30.0), result)
  }
}
