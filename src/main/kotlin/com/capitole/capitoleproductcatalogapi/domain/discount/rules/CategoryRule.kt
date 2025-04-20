package com.capitole.capitoleproductcatalogapi.domain.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Product

class CategoryRule(private val target: Category, private val discountPercentage: Double) : DiscountRule {
  override fun applyTo(product: Product) =
      if (product.category == target) DiscountPercentage(discountPercentage)
      else DiscountPercentage(0.0)
}
