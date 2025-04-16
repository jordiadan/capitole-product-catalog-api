package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {
  @Bean
  fun getProductCatalog() = GetProductCatalog()
}
