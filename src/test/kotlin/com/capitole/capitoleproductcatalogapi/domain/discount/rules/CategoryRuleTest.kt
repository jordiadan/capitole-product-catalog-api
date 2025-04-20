package com.capitole.capitoleproductcatalogapi.domain.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CategoryRuleTest {

  @Test
  fun `applies configured percentage when categories match`() {
    val rule = CategoryRule(Category.ELECTRONICS, 42.0)
    val product = Product(
        sku = SKU("SKU0001"),
        description = Description("x"),
        price = Price(10.0),
        category = Category.ELECTRONICS
    )
    assertEquals(DiscountPercentage(42.0), rule.applyTo(product))
  }

  @Test
  fun `returns zero when categories do not match`() {
    val rule = CategoryRule(Category.HOME_AND_KITCHEN, 99.0)
    val product = Product(
        sku = SKU("SKU0001"),
        description = Description("x"),
        price = Price(10.0),
        category = Category.ELECTRONICS
    )
    assertEquals(DiscountPercentage(0.0), rule.applyTo(product))
  }
}
