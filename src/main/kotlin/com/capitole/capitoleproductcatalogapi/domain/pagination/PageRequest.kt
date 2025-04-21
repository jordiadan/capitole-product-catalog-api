package com.capitole.capitoleproductcatalogapi.domain.pagination

data class PageRequest(
  val page: Int,
  val size: Int
) {
  /*
   TODO: Thorw custom exceptions when:
    1. Page is negative
    2. Size is negative
   */

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
