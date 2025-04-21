package com.capitole.capitoleproductcatalogapi.domain.product.discount

import com.capitole.capitoleproductcatalogapi.domain.product.Product

interface DiscountService {
  fun getApplicableDiscount(product: Product): DiscountPercentage
}
