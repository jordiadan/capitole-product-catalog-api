package com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product

import com.capitole.capitoleproductcatalogapi.domain.pagination.PageRequest
import com.capitole.capitoleproductcatalogapi.domain.product.*
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(
  private val jdbcTemplate: NamedParameterJdbcTemplate,
  private val sqlBuilder: ProductSQLBuilder
) : ProductRepository {

  override fun findAll(
      categoryFilter: Category?,
      sort: SortSpec?,
      pageRequest: PageRequest
  ): List<Product> {
    val params = MapSqlParameterSource()
      val sql = sqlBuilder.buildFindAllQuery(categoryFilter, sort, params)
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

