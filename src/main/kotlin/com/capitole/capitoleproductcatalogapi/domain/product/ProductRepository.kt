package com.capitole.capitoleproductcatalogapi.domain.product

import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec

interface ProductRepository {
  fun findAll(categoryFilter: Category? = null, sort: SortSpec? = null): List<Product>
}
