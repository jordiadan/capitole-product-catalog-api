package com.capitole.capitoleproductcatalogapi.domain.product

enum class Category {
  ELECTRONICS,
  HOME_AND_KITCHEN,
  CLOTHING,
  ACCESSORIES,
  SPORTS,
  STATIONERY,
  TOYS_AND_GAMES,
  MUSICAL_INSTRUMENTS,
  FOOTWEAR,
  HOME_APPLIANCES;

  companion object {
    fun from(value: String): Category {
      return runCatching { valueOf(value) }.getOrElse { throw CategoryNotFoundException(value) }
    }
  }
}
