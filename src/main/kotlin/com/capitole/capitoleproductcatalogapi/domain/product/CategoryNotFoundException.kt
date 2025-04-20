package com.capitole.capitoleproductcatalogapi.domain.product

class CategoryNotFoundException(value: String) : RuntimeException("Category $value not found")
