package com.capitole.capitoleproductcatalogapi.domain

data class Product(
  val sku: SKU,
  val description: Description,
  val price: Price,
  val category: Category
) {
  fun calculatePriceAfterDiscount(discountPercent: DiscountPercentage): String {
    TODO("Not yet implemented")
  }
}
