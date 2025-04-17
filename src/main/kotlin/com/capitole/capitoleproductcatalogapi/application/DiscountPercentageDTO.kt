package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.DiscountPercentage


data class DiscountPercentageDTO (val value: String) {
  companion object {
    fun fromDomain(domain: DiscountPercentage): DiscountPercentageDTO {
      val formatted = String.format("%.2f", domain.value)
      return DiscountPercentageDTO(formatted)
    }
  }
}

fun DiscountPercentage.toDTO(): DiscountPercentageDTO = DiscountPercentageDTO.fromDomain(this)

