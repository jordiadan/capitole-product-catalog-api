package com.capitole.capitoleproductcatalogapi.infrastructure

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductCatalogController {

  @GetMapping("/products")
  fun getProductCatalog(): ResponseEntity<Any> {
    val body = """{}"""
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
  }
}
