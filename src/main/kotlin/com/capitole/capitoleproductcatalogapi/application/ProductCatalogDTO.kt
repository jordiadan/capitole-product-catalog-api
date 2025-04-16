package com.capitole.capitoleproductcatalogapi.application

data class ProductCatalogDTO(val products: List<ProductDTO>) {
  data class ProductDTO(
    val sku: String,
    val description: String,
    val price: String,
    val discountPercentage: String,
    val finalPrice: String,
    val category: String
  )
}
