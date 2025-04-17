package com.capitole.capitoleproductcatalogapi.domain

data class DiscountPercentage(val value: Double) {
/*
   TODO should reject invalid values
    1. Negative values
    2. Above 100
*/
}
