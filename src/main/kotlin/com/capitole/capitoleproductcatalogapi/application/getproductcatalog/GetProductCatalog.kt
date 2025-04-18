package com.capitole.capitoleproductcatalogapi.application.getproductcatalog

import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.SortOrder

class GetProductCatalog(
  private val productRepository: ProductRepository,
  private val discountService: DiscountService
) {
  fun execute(
    categoryFilter: Category? = null,
    sortField: SortField?,
    sortOrder: SortOrder,
  ): ProductCatalogDTO {
    val products = productRepository.findAll(categoryFilter, sortField, sortOrder)
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
