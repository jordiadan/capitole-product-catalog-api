package com.capitole.capitoleproductcatalogapi.domain.product.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage

class CategoryRule(private val target: Category, private val discountPercentage: Double) : DiscountRule {
  override fun applyTo(product: Product) =
      if (product.category == target) DiscountPercentage(discountPercentage)
      else DiscountPercentage(0.0)
}
