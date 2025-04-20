package com.capitole.capitoleproductcatalogapi.infrastructure.configuration

import com.capitole.capitoleproductcatalogapi.domain.discount.DefaultDiscountService
import com.capitole.capitoleproductcatalogapi.domain.discount.DiscountService
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.CategoryRule
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.DiscountRule
import com.capitole.capitoleproductcatalogapi.domain.discount.rules.SkuEndsWith5Rule
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {
  @Bean
  fun electronicsRule(@Value("\${discount.category-rules.ELECTRONICS}") discount: Double) =
      CategoryRule(Category.ELECTRONICS, discount)

  @Bean
  fun homeAndKitchenRule(@Value("\${discount.category-rules.HOME_AND_KITCHEN}") discount: Double) =
      CategoryRule(Category.HOME_AND_KITCHEN, discount)

  @Bean
  fun specialSkuRule() = SkuEndsWith5Rule()

  @Bean
  fun defaultDiscountService(rules: List<DiscountRule>): DiscountService =
      DefaultDiscountService(rules)
}
