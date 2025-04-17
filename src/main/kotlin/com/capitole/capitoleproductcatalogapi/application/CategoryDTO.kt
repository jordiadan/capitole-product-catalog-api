package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.Category

data class CategoryDTO(
  private val code: String,
  val displayName: String
)

fun Category.toDTO(): CategoryDTO = CategoryDTO(
    code = this.name,
    displayName = when (this) {
      Category.ELECTRONICS -> "Electronics"
      Category.HOME_AND_KITCHEN -> "Home & Kitchen"
      Category.CLOTHING -> "Clothing"
      Category.ACCESSORIES -> "Accessories"
      Category.SPORTS -> "Sports"
      Category.STATIONERY -> "Stationery"
      Category.TOYS_AND_GAMES -> "Toys & Games"
      Category.MUSICAL_INSTRUMENTS -> "Musical Instruments"
      Category.FOOTWEAR -> "Footwear"
      Category.HOME_APPLIANCES -> "Home Appliances"
    }
)
