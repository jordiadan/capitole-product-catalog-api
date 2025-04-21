package com.capitole.capitoleproductcatalogapi.domain.pagination

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PageRequestTest {

  @Test
  fun `of(null, null) returns defaults`() {
    val pr = PageRequest.of(null, null)
    assertEquals(0, pr.page)
    assertEquals(20, pr.size)
  }

  @ParameterizedTest(name = "pageParam={0}, sizeParam={1} yields page={2}, size={3}")
  @CsvSource(
      "0, 1, 0, 1",
      "2, 50, 2, 50",
      "-1, 5, 0, 5",
      "-5, null, 0, 20",
      "3, 0, 3, 20",
      "3, -10, 3, 20",
      "null, 10, 0, 10",
      "1, null, 1, 20"
  )
  fun `of parameterized`(
    pageParam: String?,
    sizeParam: String?,
    expectedPage: Int,
    expectedSize: Int
  ) {
    val pageArg = pageParam?.takeIf { it != "null" }?.toInt()
    val sizeArg = sizeParam?.takeIf { it != "null" }?.toInt()
    val pr = PageRequest.of(pageArg, sizeArg)
    assertEquals(expectedPage, pr.page)
    assertEquals(expectedSize, pr.size)
  }
}
