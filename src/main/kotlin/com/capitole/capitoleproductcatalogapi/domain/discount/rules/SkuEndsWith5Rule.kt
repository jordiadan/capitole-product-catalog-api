package com.capitole.capitoleproductcatalogapi.domain.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.Product

class SkuEndsWith5Rule : DiscountRule {
  override fun applyTo(product: Product) =
      if (product.sku.value.endsWith("5")) DiscountPercentage(30.0)
      else DiscountPercentage(0.0)
}
