package com.capitole.capitoleproductcatalogapi.domain.product.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage

class SkuEndsWith5Rule : DiscountRule {
  override fun applyTo(product: Product) =
      if (product.sku.value.endsWith("5")) DiscountPercentage(30.0)
      else DiscountPercentage(0.0)
}
