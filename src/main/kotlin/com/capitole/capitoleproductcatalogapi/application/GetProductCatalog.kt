package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.ProductRepository

class GetProductCatalog(private val productRepository: ProductRepository) {
  fun execute(): ProductCatalogDTO {
    // TODO Implement use case instead of using dummy content
    return ProductCatalogDTO(listOf())
  }

}
