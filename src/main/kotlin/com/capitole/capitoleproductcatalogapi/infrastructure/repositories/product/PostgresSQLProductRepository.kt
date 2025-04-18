package com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.SortOrder
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(
  private val jdbcTemplate: NamedParameterJdbcTemplate,
  private val sqlBuilder: ProductSQLBuilder
) : ProductRepository {

  override fun findAll(
    categoryFilter: Category?,
    sortField: SortField?,
    sortOrder: SortOrder
  ): List<Product> {
    val params = MapSqlParameterSource()
    val sql = sqlBuilder.buildFindAllQuery(categoryFilter, sortField, sortOrder, params)
    return jdbcTemplate.query(sql, params, productRowMapper)
  }

  companion object {
    private val productRowMapper = RowMapper<Product> { rs, _ ->
      Product(
          sku = SKU(rs.getString("sku")),
          description = Description(rs.getString("description")),
          price = Price(rs.getDouble("price")),
          category = Category.valueOf(rs.getString("category"))
      )
    }
  }
}

