package com.capitole.capitoleproductcatalogapi.infrastructure.repositories

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.SortField
import com.capitole.capitoleproductcatalogapi.domain.SortOrder
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource

class ProductSQLBuilder {

  private val fieldToColumn = mapOf(
      SortField.SKU to "sku",
      SortField.PRICE to "price",
      SortField.DESCRIPTION to "description",
      SortField.CATEGORY to "category"
  )

  fun buildFindAllQuery(
    category: Category?,
    sortField: SortField?,
    sortOrder: SortOrder,
    params: MapSqlParameterSource
  ): String {
    val query = StringBuilder(
        """
      SELECT sku, description, price, category
        FROM products
    """.trimIndent()
    )

    category?.also {
      query.append("\n WHERE category = CAST(:category AS category_enum)")
      params.addValue("category", it.name)
    }

    sortField?.also { field ->
      val column = fieldToColumn[field]
        ?: throw IllegalArgumentException("Unsupported sort field: $field")
      query.append("\n ORDER BY $column ${sortOrder.name}")
    }

    return query.toString()
  }
}
