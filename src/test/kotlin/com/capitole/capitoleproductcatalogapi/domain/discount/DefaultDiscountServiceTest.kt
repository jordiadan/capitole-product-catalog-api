package com.capitole.capitoleproductcatalogapi.domain.discount

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DefaultDiscountService
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.discount.rules.CategoryRule
import com.capitole.capitoleproductcatalogapi.domain.product.discount.rules.SkuEndsWith5Rule
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
  fun `sku ending with 5 overrides category discount`() {
    val product = Product(SKU("SKU0005"), Description("x"), Price(200.0), Category.HOME_AND_KITCHEN)
    assertEquals(DiscountPercentage(30.0), discountService.getApplicableDiscount(product))
  }

  @Test
  fun `electronics category gets 15 percent discount`() {
    val product = Product(SKU("SKU0001"), Description("x"), Price(200.0), Category.ELECTRONICS)
    assertEquals(DiscountPercentage(15.0), discountService.getApplicableDiscount(product))
  }

  @Test
  fun `home and kitchen category gets 25 percent discount`() {
    val product = Product(SKU("SKU0002"), Description("x"), Price(200.0), Category.HOME_AND_KITCHEN)
    assertEquals(DiscountPercentage(25.0), discountService.getApplicableDiscount(product))
  }

  @Test
  fun `other categories get zero discount`() {
    val product = Product(SKU("SKU0003"), Description("x"), Price(200.0), Category.CLOTHING)
    assertEquals(DiscountPercentage(0.0), discountService.getApplicableDiscount(product))
  }
}
