package com.capitole.capitoleproductcatalogapi.domain

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.CategoryNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.Test

class CategoryTest {

  @ParameterizedTest(name = "valueOf for {0}")
  @EnumSource(Category::class)
  fun `valueOf should return the same enum constant`(category: Category) {
    val name = category.name

    val result = Category.valueOf(name)

    assertEquals(category, result)
  }

  @Test
  fun `from should throw when given invalid value`() {
    val invalidCategory = "INVALID_CATEGORY"
    val ex = assertThrows<CategoryNotFoundException> {
      Category.from(invalidCategory)
    }

    assertEquals("Category $invalidCategory not found", ex.message)
  }

}
