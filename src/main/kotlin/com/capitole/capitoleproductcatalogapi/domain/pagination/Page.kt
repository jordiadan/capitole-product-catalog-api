package com.capitole.capitoleproductcatalogapi.domain.pagination

data class Page<T>(
  val content: List<T>,
  val pageNumber: Int,
  val pageSize: Int,
  val totalElements: Long
) {
  val totalPages: Int
    get() = if (pageSize == 0) 0 else ((totalElements + pageSize - 1) / pageSize).toInt()
}
