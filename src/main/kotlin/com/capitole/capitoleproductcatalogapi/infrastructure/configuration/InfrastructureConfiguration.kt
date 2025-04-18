package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product.PostgresSQLProductRepository
import com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product.ProductSQLBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class InfrastructureConfiguration {
    @Bean
    fun productRepository(
        jdbcTemplate: NamedParameterJdbcTemplate,
        productSQLBuilder: ProductSQLBuilder
    ): ProductRepository =
        PostgresSQLProductRepository(jdbcTemplate, productSQLBuilder)

    @Bean
    fun productSQLBuilder() = ProductSQLBuilder()
}
