package com.capitole.capitoleproductcatalogapi.domain.discount

import com.capitole.capitoleproductcatalogapi.domain.discount.rules.DiscountRule
import com.capitole.capitoleproductcatalogapi.domain.product.Product

class DefaultDiscountService(val rules: List<DiscountRule>) : DiscountService {
  override fun getApplicableDiscount(product: Product): DiscountPercentage =
      rules.map { it.applyTo(product) }.maxByOrNull { it.value } ?: DiscountPercentage(0.0)
}
