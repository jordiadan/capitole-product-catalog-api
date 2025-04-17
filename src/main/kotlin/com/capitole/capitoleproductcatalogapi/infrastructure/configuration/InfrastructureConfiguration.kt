package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.infrastructure.repositories.InMemoryProductRepository
import com.capitole.capitoleproductcatalogapi.infrastructure.services.DefaultDiscountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InfrastructureConfiguration {
  @Bean
  fun discountService() = DefaultDiscountService()

  @Bean
  fun productRepository() = InMemoryProductRepository()
}
