package com.capitole.capitoleproductcatalogapi.domain.product.sort

data class SortSpec(
  val field: SortField,
  val order: SortOrder
) {
  companion object {
    fun of(sortField: SortField?, order: SortOrder): SortSpec = SortSpec(sortField ?: SortField.SKU, order)
  }
}
