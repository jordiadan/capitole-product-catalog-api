package com.capitole.capitoleproductcatalogapi.domain.pagination

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PageTest {

  @Test
  fun `totalPages is zero when pageSize is zero`() {
    val page = Page(
        content = listOf("a", "b", "c"),
        pageNumber = 0,
        pageSize = 0,
        totalElements = 42
    )

    assertEquals(0, page.totalPages)
  }

  @Test
  fun `totalPages computes exact division`() {
    val pageSize = 5
    val totalElements = 20L
    val page = Page(
        content = emptyList<String>(),
        pageNumber = 1,
        pageSize = pageSize,
        totalElements = totalElements
    )

    assertEquals(4, page.totalPages)
  }

  @Test
  fun `totalPages computes with remainder rounds up`() {
    val pageSize = 4
    val totalElements = 10L
    val page = Page(
        content = emptyList<String>(),
        pageNumber = 2,
        pageSize = pageSize,
        totalElements = totalElements
    )

    assertEquals(3, page.totalPages)
  }

  @Test
  fun `properties reflect constructor arguments`() {
    val items = listOf("x", "y")
    val pageNumber = 3
    val pageSize = 2
    val totalElements = 7L

    val page = Page(
        content = items,
        pageNumber = pageNumber,
        pageSize = pageSize,
        totalElements = totalElements
    )

    assertEquals(items, page.content)
    assertEquals(pageNumber, page.pageNumber)
    assertEquals(pageSize, page.pageSize)
    assertEquals(totalElements, page.totalElements)
  }
}
