package com.capitole.capitoleproductcatalogapi.domain.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.product.Product

interface DiscountRule {
  fun applyTo(product: Product): DiscountPercentage
}
