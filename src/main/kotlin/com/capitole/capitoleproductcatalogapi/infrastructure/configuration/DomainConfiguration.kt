package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.domain.discount.DefaultDiscountService
import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.DiscountRule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {
  @Bean
  fun defaultDiscountService(rules: List<DiscountRule>): DiscountService =
      DefaultDiscountService(rules)
}
