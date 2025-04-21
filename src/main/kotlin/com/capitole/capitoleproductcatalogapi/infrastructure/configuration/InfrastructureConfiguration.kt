package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product.InMemoryProductRepository
import com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product.PostgresSQLProductRepository
import com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product.ProductSQLBuilder
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class InfrastructureConfiguration {

  // TODO: Just to test "main" environment with dummy data (OpenApi)
  @Bean
  @Profile("!integration-test")
  fun inMemoryProductRepository() = InMemoryProductRepository()

  @Bean
  @Profile("integration-test")
  fun productRepository(
    jdbcTemplate: NamedParameterJdbcTemplate,
    productSQLBuilder: ProductSQLBuilder
  ): ProductRepository =
      PostgresSQLProductRepository(jdbcTemplate, productSQLBuilder)

  @Bean
  fun productSQLBuilder() = ProductSQLBuilder()

  @Bean
  fun customOpenApi(): OpenAPI =
      OpenAPI()
          .info(
              Info()
                  .title("Product Catalog API")
                  .version("1.0.0")
                  .description("Retrieve and filter products with real‑time discount rules")
          )
}
