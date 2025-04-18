package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.domain.Category
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductCatalogController(private val getProductCatalog: GetProductCatalog) {

  @GetMapping("/products")
  fun getProductCatalog(
    @RequestParam(name = "category", required = false) category: Category?
  ): ResponseEntity<GetProductCatalogResponse> {
    val getProductCatalogDTO = getProductCatalog.execute(category)
    val getProductCatalogResponse = getProductCatalogDTO.toGetProductCatalogResponse()
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(getProductCatalogResponse)
  }
}

