package com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto

import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.toDTO
import com.capitole.capitoleproductcatalogapi.domain.pagination.Page
import com.capitole.capitoleproductcatalogapi.domain.pagination.PageRequest
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortOrder
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec

class GetProductCatalog(
    private val productRepository: ProductRepository,
    private val discountService: DiscountService
) {
    fun execute(
        categoryFilter: Category? = null,
        sortField: SortField?,
        sortOrder: SortOrder,
        pageRequest: PageRequest
    ): ProductCatalogDTO {
        val sortSpec = SortSpec.of(sortField, sortOrder)
        val products = productRepository.findAll(categoryFilter, sortSpec, pageRequest)
        return products.toProductCatalogDTO(discountService)
    }
}

private fun Page<Product>.toProductCatalogDTO(discountService: DiscountService): ProductCatalogDTO {
    val productDTOs = this.content.map { product ->
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
    return ProductCatalogDTO(
        products = productDTOs,
        page = pageNumber,
        size = pageSize,
        totalElements = totalElements,
        totalPages = totalPages
    )
}
