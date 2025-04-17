package com.capitole.capitoleproductcatalogapi.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class CategoryTest {

  @ParameterizedTest(name = "valueOf for {0}")
   @EnumSource(Category::class)
   fun `valueOf should return the same enum constant`(category: Category) {
     val name = category.name

    val result = Category.valueOf(name)

     assertEquals(category, result)
   }

 }
