package com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec

class ProductSQLBuilder {

    private val fieldToColumn = mapOf(
        SortField.SKU to "sku",
        SortField.PRICE to "price",
        SortField.DESCRIPTION to "description",
        SortField.CATEGORY to "category"
    )

    fun buildFindAllQuery(
        category: Category?,
        sort: SortSpec?
    ): String {
        val sql = StringBuilder(
            """
      SELECT sku,
             description,
             price,
             category
        FROM products
    """.trimIndent()
        )

        category?.also {
            sql.append("\n WHERE category = CAST(:category AS category_enum)")
        }

        sort?.let { spec ->
            val col = fieldToColumn[spec.field]
                ?: error("Unsupported sort field: ${spec.field}")
            sql.append("\n ORDER BY $col ${spec.order.name}")
        }

        sql.append("\n LIMIT :limit OFFSET :offset")
        return sql.toString()
    }

    fun buildCountQuery(category: Category?): String {
        val sql = StringBuilder("SELECT COUNT(*) FROM products")
        category?.also {
            sql.append("\n WHERE category = CAST(:category AS category_enum)")
        }
        return sql.toString()
    }
}
