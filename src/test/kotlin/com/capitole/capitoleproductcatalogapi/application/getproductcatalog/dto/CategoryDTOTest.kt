package com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class CategoryDtoTest {

  @ParameterizedTest(name = "Category.{0} → displayName=\"{1}\"")
  @CsvSource(
      "ELECTRONICS,Electronics",
      "HOME_AND_KITCHEN,Home & Kitchen",
      "CLOTHING,Clothing",
      "ACCESSORIES,Accessories",
      "SPORTS,Sports",
      "STATIONERY,Stationery",
      "TOYS_AND_GAMES,Toys & Games",
      "MUSICAL_INSTRUMENTS,Musical Instruments",
      "FOOTWEAR,Footwear",
      "HOME_APPLIANCES,Home Appliances"
  )
  fun `toDTO should set code and displayName correctly`(
    enumName: String,
    expectedDisplay: String
  ) {
    val category = Category.valueOf(enumName)
    val dto = category.toDTO()

    assertEquals(expectedDisplay, dto.displayName)

    val codeField = CategoryDTO::class.java
        .getDeclaredField("code")
        .apply { isAccessible = true }

    val actualCode = codeField.get(dto) as String
    assertEquals(enumName, actualCode)
  }
}
