package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository

class GetProductCatalog(
  private val productRepository: ProductRepository,
  private val discountService: DiscountService
) {
  fun execute(categoryFilter: Category? = null): ProductCatalogDTO {
    val products = productRepository.findAll(categoryFilter)
    return products.toProductCatalogDTO(discountService)
  }
}

private fun List<Product>.toProductCatalogDTO(discountService: DiscountService): ProductCatalogDTO {
  val productDTOs = this.map { product ->
    val discount = discountService.getApplicableDiscount(product)
    val finalPrice = product.calculatePriceAfterDiscount(discount)
    ProductCatalogDTO.ProductDTO(
        sku = product.sku.value,
        description = product.description.value,
        price = product.price.toDTO(),
        discountPercentage = discount.toDTO(),
        finalPrice = finalPrice,
        category = product.category.toDTO()
    )
  }
  return ProductCatalogDTO(productDTOs)
}
