package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository

class GetProductCatalog(private val productRepository: ProductRepository) {
  fun execute(): ProductCatalogDTO {
    val products = productRepository.findAll()
    return products.toPRoductCatalogDTO()
  }

}

private fun List<Product>.toPRoductCatalogDTO(): ProductCatalogDTO {
  val productDTOs = this.map { product ->
    val discount = ""
    val finalPrice = ""
    ProductCatalogDTO.ProductDTO(
        sku = product.sku.value,
        description = product.description.value,
        price = product.price.value.toString(),
        discountPercentage = discount,
        finalPrice = finalPrice,
        category = product.category.name
    )
  }
  return ProductCatalogDTO(productDTOs)
}
