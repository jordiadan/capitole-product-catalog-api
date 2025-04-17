package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.DiscountPercentage
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
    val discount = DiscountPercentage(0.0)
    val finalPrice = product.calculatePriceAfterDiscount(discount)
    ProductCatalogDTO.ProductDTO(
        sku = product.sku.value,
        description = product.description.value,
        price = product.price.value.toString(),
        discountPercentage = discount.toString(),
        finalPrice = finalPrice,
        category = product.category.toDTO()
    )
  }
  return ProductCatalogDTO(productDTOs)
}
