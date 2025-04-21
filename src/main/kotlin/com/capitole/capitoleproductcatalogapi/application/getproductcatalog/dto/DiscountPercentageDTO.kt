package com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto

import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountPercentage


data class DiscountPercentageDTO (val value: String) {
  companion object {
    fun fromDomain(domain: DiscountPercentage): DiscountPercentageDTO {
      val formatted = String.format("%.2f", domain.value)
      return DiscountPercentageDTO(formatted)
    }
  }
}

fun DiscountPercentage.toDTO(): DiscountPercentageDTO = DiscountPercentageDTO.fromDomain(this)

