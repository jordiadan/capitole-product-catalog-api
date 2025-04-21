package com.capitole.capitoleproductcatalogapi.domain

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.discount.DiscountPercentage
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

    @Test
    fun `calculatePriceAfterDiscount applies 30 percent discount correctly`() {
        val originalPrice = Price(120.00)
        val product = Product(sampleSku, sampleDescription, originalPrice, sampleCategory)

        val result = product.calculatePriceAfterDiscount(DiscountPercentage(30.0))

        assertEquals("84.00", result)
    }

    @Test
    fun `calculatePriceAfterDiscount handles 100 percent discount`() {
        val originalPrice = Price(45.50)
        val product = Product(sampleSku, sampleDescription, originalPrice, sampleCategory)

        val result = product.calculatePriceAfterDiscount(DiscountPercentage(100.0))

        assertEquals("0.00", result)
    }

    @Test
    fun `calculatePriceAfterDiscount rounds half up for fractional discount`() {
        val originalPrice = Price(10.00)
        val product = Product(sampleSku, sampleDescription, originalPrice, sampleCategory)

        val result = product.calculatePriceAfterDiscount(DiscountPercentage(33.3333))

        assertEquals("6.67", result)
    }
}
