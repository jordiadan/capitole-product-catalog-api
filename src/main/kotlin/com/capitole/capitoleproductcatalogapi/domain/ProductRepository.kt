package com.capitole.capitoleproductcatalogapi.domain

interface ProductRepository {
  fun findAll(): List<Product>
}
