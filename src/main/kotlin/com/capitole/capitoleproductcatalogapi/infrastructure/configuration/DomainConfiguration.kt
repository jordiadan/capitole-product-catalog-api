package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.domain.discount.DefaultDiscountService
import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.CategoryRule
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.DiscountRule
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.SkuEndsWith5Rule
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {
  @Bean
  fun electronicsRule() = CategoryRule(Category.ELECTRONICS, 15.0)

  @Bean
  fun homeAndKitchenRule() = CategoryRule(Category.HOME_AND_KITCHEN, 25.0)

  @Bean
  fun specialSkuRule() = SkuEndsWith5Rule()

  @Bean
  fun defaultDiscountService(rules: List<DiscountRule>): DiscountService =
      DefaultDiscountService(rules)
}
