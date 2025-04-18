package com.capitole.capitoleproductcatalogapi.domain.product

interface ProductRepository {
    fun findAll(categoryFilter: Category? = null, sortField: SortField?, sortOrder: SortOrder): List<Product>
}
