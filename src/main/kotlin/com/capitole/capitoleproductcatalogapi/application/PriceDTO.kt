package com.capitole.capitoleproductcatalogapi.application

import com.capitole.capitoleproductcatalogapi.domain.Price

data class PriceDTO (val value: String) {
  companion object {
    fun fromDomain(domain: Price): PriceDTO {
      val formatted = String.format("%.2f", domain.value)
      return PriceDTO(formatted)
    }
  }
}

fun Price.toDTO(): PriceDTO = PriceDTO.fromDomain(this)
