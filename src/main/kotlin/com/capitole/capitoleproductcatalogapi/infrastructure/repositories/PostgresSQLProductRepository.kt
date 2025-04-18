package com.capitole.capitoleproductcatalogapi.infrastructure.repositories

import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.Description
import com.capitole.capitoleproductcatalogapi.domain.Price
import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.SKU
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : ProductRepository {
  override fun findAll(categoryFilter: Category?): List<Product> {
    val sql = StringBuilder().apply {
      append("""
        SELECT sku,
               description,
               price,
               category
          FROM products
      """.trimIndent())
      if (categoryFilter != null) {
        append("\n WHERE category = CAST(:category AS category_enum)")
      }
    }.toString()

    val params = mutableMapOf<String, Any>()
    categoryFilter?.let { params["category"] = it.name }

    return jdbcTemplate.query(sql, params) { rs, _ ->
      Product(
          sku         = SKU(rs.getString("sku")),
          description = Description(rs.getString("description")),
          price       = Price(rs.getDouble("price")),
          category    = Category.valueOf(rs.getString("category"))
      )
    }
  }
}
