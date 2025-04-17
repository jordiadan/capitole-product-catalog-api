package com.capitole.capitoleproductcatalogapi.domain.discount

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.DiscountPercentage
import com.capitole.capitoleproductcatalogapi.domain.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.Product

class DefaultDiscountService : DiscountService {
  override fun getApplicableDiscount(product: Product): DiscountPercentage {
    if (product.sku.value.endsWith("5")) return DiscountPercentage(30.0)
    return when (product.category) {
      Category.HOME_AND_KITCHEN -> DiscountPercentage(25.0)
      Category.ELECTRONICS      -> DiscountPercentage(15.0)
      else                      -> DiscountPercentage(0.0)
    }
  }
}
