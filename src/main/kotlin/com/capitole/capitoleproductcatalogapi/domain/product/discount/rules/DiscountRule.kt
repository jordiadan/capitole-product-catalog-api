package com.capitole.capitoleproductcatalogapi.domain.product.discount.rules

import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage

interface DiscountRule {
  fun applyTo(product: Product): DiscountPercentage
}
