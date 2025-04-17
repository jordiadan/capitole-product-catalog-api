package com.capitole.capitoleproductcatalogapi.application

data class ProductCatalogDTO(val products: List<ProductDTO>) {
  data class ProductDTO(
    val sku: String,
    val description: String,
    val price: PriceDTO,
    val discountPercentage: DiscountPercentageDTO,
    val finalPrice: String,
    val category: CategoryDTO
  )
}
