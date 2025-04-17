package com.capitole.capitoleproductcatalogapi.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun `calculatePriceAfterDiscount returns original price when discount is zero`() {
        val sampleSku = SKU("SKU0001")
        val sampleDescription = Description("Sample Product")
        val sampleCategory = Category.ELECTRONICS
        val originalPrice = Price(100.00)
        val product = Product(sampleSku, sampleDescription, originalPrice, sampleCategory)

        val result = product.calculatePriceAfterDiscount(DiscountPercentage(0.0))

        assertEquals("100.00", result)
    }
}
