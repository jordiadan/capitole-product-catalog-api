package com.capitole.capitoleproductcatalogapi.infrastructure.repositories

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.Description
import com.capitole.capitoleproductcatalogapi.domain.Price
import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.SKU
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : ProductRepository {
  override fun findAll(): List<Product> {
    val sql = """SELECT sku, description, price, category FROM products""".trimIndent()
    return jdbcTemplate.query(sql, { rs, _ ->
      Product(
          sku = SKU(rs.getString("sku")),
          description = Description(rs.getString("description")),
          price = Price(rs.getDouble("price")),
          category = Category.valueOf(rs.getString("category")),
      )
    })
  }
}
