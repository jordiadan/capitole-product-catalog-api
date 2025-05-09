package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.dto.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.domain.pagination.PageRequest
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortOrder
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductCatalogController(
  private val getProductCatalog: GetProductCatalog
) : ProductCatalogApi {

  @GetMapping("/products", produces = [MediaType.APPLICATION_JSON_VALUE])
  override fun getProductCatalog(
    category: String?,
    sortField: SortField?,
    sortOrder: SortOrder,
    page: Int,
    size: Int
  ): ResponseEntity<GetProductCatalogResponse> {
    val catEnum = category?.let { Category.from(it) }
    val pageRequest = PageRequest.of(page, size)
    val dto = getProductCatalog.execute(catEnum, sortField, sortOrder, pageRequest)
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto.toGetProductCatalogResponse())
  }
}
