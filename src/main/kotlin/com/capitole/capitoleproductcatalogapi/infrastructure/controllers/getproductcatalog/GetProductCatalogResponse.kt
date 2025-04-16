package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.application.ProductCatalogDTO

data class GetProductCatalogResponse(
  val products: List<ProductResponse>
) {
  data class ProductResponse(
    val sku: String,
    val description: String,
    val price: String,
    val discountPercentage: String,
    val finalPrice: String,
    val category: String
  )
}

fun ProductCatalogDTO.toGetProductCatalogResponse(): GetProductCatalogResponse = GetProductCatalogResponse(
    products = this.products.map { productDTO ->
      GetProductCatalogResponse.ProductResponse(
          sku = productDTO.sku,
          description = productDTO.description,
          price = productDTO.price,
          discountPercentage = productDTO.discountPercentage,
          finalPrice = productDTO.finalPrice,
          category = productDTO.category
      )
    }
)
