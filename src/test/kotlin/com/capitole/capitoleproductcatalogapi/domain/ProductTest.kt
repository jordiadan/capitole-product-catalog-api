package com.capitole.capitoleproductcatalogapi.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductTest {
    private val sampleSku = SKU("SKU0001")
    private val sampleDescription = Description("Sample Product")
    private val sampleCategory = Category.ELECTRONICS

    @Test
    fun `calculatePriceAfterDiscount returns original price when discount is zero`() {
        val originalPrice = Price(100.00)
        val product = Product(sampleSku, sampleDescription, originalPrice, sampleCategory)

        val result = product.calculatePriceAfterDiscount(DiscountPercentage(0.0))

        assertEquals("100.00", result)
    }

    @Test
    fun `calculatePriceAfterDiscount applies 15 percent discount correctly`() {
        val originalPrice = Price(19.99)
        val product = Product(sampleSku, sampleDescription, originalPrice, sampleCategory)

        val result = product.calculatePriceAfterDiscount(DiscountPercentage(15.0))

        assertEquals("16.99", result)
    }
}
