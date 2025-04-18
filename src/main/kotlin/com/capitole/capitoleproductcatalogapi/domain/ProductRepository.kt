package com.capitole.capitoleproductcatalogapi.domain

interface ProductRepository {
  fun findAll(categoryFilter: Category? = null): List<Product>
}
