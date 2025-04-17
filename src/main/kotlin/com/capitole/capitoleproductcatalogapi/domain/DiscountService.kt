package com.capitole.capitoleproductcatalogapi.domain

interface DiscountService {
  fun getApplicableDiscount(product: Product): DiscountPercentage
}
