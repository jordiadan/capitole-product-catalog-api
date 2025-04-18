package com.capitole.capitoleproductcatalogapi.infrastructure.repositories

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.Description
import com.capitole.capitoleproductcatalogapi.domain.Price
import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.SKU
import com.capitole.capitoleproductcatalogapi.domain.SortField
import com.capitole.capitoleproductcatalogapi.domain.SortOrder
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : ProductRepository {
  override fun findAll(
    categoryFilter: Category?,
    sortField: SortField?,
    sortOrder: SortOrder
  ): List<Product> {
    val sqlBuilder = StringBuilder(
        """
            SELECT sku,
                   description,
                   price,
                   category
              FROM products
            """.trimIndent()
    )

    val params = MapSqlParameterSource()

    categoryFilter?.let {
      sqlBuilder.append("\nWHERE category = CAST(:category AS category_enum)")
      params.addValue("category", it.name)
    }

    sortField?.let { field ->
      val column = when (field) {
        SortField.PRICE -> "price"
      }
      sqlBuilder.append("\nORDER BY $column ${sortOrder.name}")
    }

    val sql = sqlBuilder.toString()
    return jdbcTemplate.query(sql, params, productRowMapper)
  }

  private val productRowMapper = RowMapper<Product> { rs, _ ->
    Product(
        sku = SKU(rs.getString("sku")),
        description = Description(rs.getString("description")),
        price = Price(rs.getDouble("price")),
        category = Category.valueOf(rs.getString("category"))
    )
  }
}
