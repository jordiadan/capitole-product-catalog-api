package com.capitole.capitoleproductcatalogapi.domain.pagination

data class PageRequest(
  val page: Int,
  val size: Int
) {
  init {
    // TODO: Throw custom domain exceptions
    require(page >= 0) { "page index must be non‐negative, was $page" }
    require(size > 0) { "page size must be positive, was $size" }
  }

  companion object {
    private const val DEFAULT_PAGE = 0
    private const val DEFAULT_PAGE_SIZE = 20
    fun of(pageParam: Int?, sizeParam: Int?): PageRequest {
      val p = pageParam?.takeIf { it >= 0 } ?: DEFAULT_PAGE
      val s = sizeParam?.takeIf { it > 0 } ?: DEFAULT_PAGE_SIZE
      return PageRequest(p, s)
    }
  }
}
