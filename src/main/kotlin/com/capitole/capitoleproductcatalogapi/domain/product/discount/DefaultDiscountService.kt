package com.capitole.capitoleproductcatalogapi.domain.product.discount

import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.discount.rules.DiscountRule

class DefaultDiscountService(private val rules: List<DiscountRule>) : DiscountService {
  override fun getApplicableDiscount(product: Product): DiscountPercentage =
      rules.map { it.applyTo(product) }.maxByOrNull { it.value } ?: DiscountPercentage(0.0)
}
