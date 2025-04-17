package com.capitole.capitoleproductcatalogapi.infrastructure.repositories

import com.capitole.capitoleproductcatalogapi.domain.Product
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : ProductRepository {
  override fun findAll(): List<Product> {
    TODO("Not yet implemented")
  }
}
