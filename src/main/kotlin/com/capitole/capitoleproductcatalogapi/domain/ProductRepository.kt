package com.capitole.capitoleproductcatalogapi.domain

interface ProductRepository {
    fun findAll(categoryFilter: Category? = null, sortField: SortField?, sortOrder: SortOrder): List<Product>
}
