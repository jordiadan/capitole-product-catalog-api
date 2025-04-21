package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {
  @Bean
  fun getProductCatalog(productRepository: ProductRepository, discountService: DiscountService) =
      GetProductCatalog(productRepository, discountService)
}
