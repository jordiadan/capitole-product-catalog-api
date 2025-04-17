package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.domain.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {
  @Bean
  fun getProductCatalog(productRepository: ProductRepository, discountService: DiscountService) =
      GetProductCatalog(productRepository, discountService)
}
