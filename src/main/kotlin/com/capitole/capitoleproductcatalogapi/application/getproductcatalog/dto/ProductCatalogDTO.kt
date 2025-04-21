package com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto

import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.PriceDTO

data class ProductCatalogDTO(
  val products: List<ProductDTO>,
  val page: Int,
  val size: Int,
  val totalElements: Long,
  val totalPages: Int
) {
  data class ProductDTO(
    val sku: String,
    val description: String,
    val price: PriceDTO,
    val discountPercentage: DiscountPercentageDTO,
    val finalPrice: String,
    val category: CategoryDTO
  )
}
