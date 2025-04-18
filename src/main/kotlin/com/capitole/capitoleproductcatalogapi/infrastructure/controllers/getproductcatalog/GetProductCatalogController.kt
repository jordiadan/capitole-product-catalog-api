package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.application.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.domain.Category
import com.capitole.capitoleproductcatalogapi.domain.SortField
import com.capitole.capitoleproductcatalogapi.domain.SortOrder
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductCatalogController(private val getProductCatalog: GetProductCatalog) {

  @GetMapping("/products", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getProductCatalog(
    @RequestParam(required = false) category: Category?,
    @RequestParam(required = false) sortField: SortField?,
    @RequestParam(required = false, defaultValue = "ASC") sortOrder: SortOrder
  ): ResponseEntity<GetProductCatalogResponse> {
    val getProductCatalogDTO = getProductCatalog.execute(category, sortField, sortOrder)
    val getProductCatalogResponse = getProductCatalogDTO.toGetProductCatalogResponse()
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(getProductCatalogResponse)
  }
}

