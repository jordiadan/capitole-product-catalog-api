package com.capitole.capitoleproductcatalogapi.domain.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.discount.rules.SkuEndsWith5Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SkuEndsWith5RuleTest {

  private val rule = SkuEndsWith5Rule()

  @Test
  fun `returns 30 percent when SKU ends with 5`() {
    val product = Product(
        sku = SKU("ABC5"),
        description = Description("x"),
        price = Price(1.0),
        category = Category.CLOTHING
    )
    assertEquals(DiscountPercentage(30.0), rule.applyTo(product))
  }

  @Test
  fun `returns zero when SKU does not end with 5`() {
    val product = Product(
        sku = SKU("ABC1"),
        description = Description("x"),
        price = Price(1.0),
        category = Category.CLOTHING
    )
    assertEquals(DiscountPercentage(0.0), rule.applyTo(product))
  }
}
