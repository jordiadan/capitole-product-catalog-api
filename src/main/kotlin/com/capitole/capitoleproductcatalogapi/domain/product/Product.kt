package com.capitole.capitoleproductcatalogapi.domain.product

import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage

data class Product(
  val sku: SKU,
  val description: Description,
  val price: Price,
  val category: Category
) {
  fun calculatePriceAfterDiscount(discountPercent: DiscountPercentage): String {
    val amountToDiscount = discountPercent.value.times(price.value).div(100.00)
    val finalPrice = price.value.minus(amountToDiscount)
    return String.format("%.2f", finalPrice)
  }
}
