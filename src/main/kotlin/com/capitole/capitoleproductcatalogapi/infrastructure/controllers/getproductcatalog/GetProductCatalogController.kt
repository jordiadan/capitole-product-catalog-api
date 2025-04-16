package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductCatalogController(private val getProductCatalog: GetProductCatalog) {

  @GetMapping("/products")
  fun getProductCatalog(): ResponseEntity<GetProductCatalogResponse> {
    val getProductCatalogDTO = getProductCatalog.execute()
    val getProductCatalogResponse = getProductCatalogDTO.toGetProductCatalogResponse()
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(getProductCatalogResponse)
  }
}

