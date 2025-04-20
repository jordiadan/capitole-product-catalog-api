package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.application.getproductcatalog.GetProductCatalog
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.SortOrder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Products", description = "Retrieve, filter & sort product catalog")
class GetProductCatalogController(private val getProductCatalog: GetProductCatalog) {
  @Operation(
      summary = "List products",
      description = "Returns all products, with optional filtering by category and sorting"
  )
  @GetMapping("/products", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getProductCatalog(
    @Parameter(description = "Filter by category (e.g. ELECTRONICS)")
    @RequestParam(name = "category", required = false) categoryParam: String?,
    @Parameter(description = "Sort field: SKU, PRICE, DESCRIPTION or CATEGORY")
    @RequestParam(required = false) sortField: SortField?,
    @Parameter(description = "Sort order: ASC or DESC")
    @RequestParam(required = false, defaultValue = "ASC") sortOrder: SortOrder
  ): ResponseEntity<GetProductCatalogResponse> {
    val category = categoryParam?.let { Category.from(it) }
    val getProductCatalogDTO = getProductCatalog.execute(category, sortField, sortOrder)
    val getProductCatalogResponse = getProductCatalogDTO.toGetProductCatalogResponse()
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(getProductCatalogResponse)
  }
}

