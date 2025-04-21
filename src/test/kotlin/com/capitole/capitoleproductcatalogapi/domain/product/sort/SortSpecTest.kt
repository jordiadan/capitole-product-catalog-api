package com.capitole.capitoleproductcatalogapi.domain.product.sort

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SortSpecTest {

  @Test
  fun `of should default field to SKU when sortField is null`() {
    val spec = SortSpec.of(null, SortOrder.ASC)

    assertEquals(SortField.SKU, spec.field, "when sortField is null, field must default to SKU")
    assertEquals(SortOrder.ASC, spec.order, "order should be preserved")
  }

  @Test
  fun `of should use provided sortField when not null`() {
    val spec = SortSpec.of(SortField.PRICE, SortOrder.DESC)

    assertEquals(SortField.PRICE, spec.field)
    assertEquals(SortOrder.DESC, spec.order)
  }

  @Test
  fun `constructor preserves both field and order`() {
    val spec = SortSpec(SortField.DESCRIPTION, SortOrder.ASC)

    assertEquals(SortField.DESCRIPTION, spec.field)
    assertEquals(SortOrder.ASC, spec.order)
  }
}
