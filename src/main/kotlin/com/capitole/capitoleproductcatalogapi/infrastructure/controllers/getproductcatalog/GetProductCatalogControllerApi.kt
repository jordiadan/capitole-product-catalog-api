package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortOrder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Products", description = "Retrieve, filter, sort & paginate product catalog")
interface ProductCatalogApi {

  @Operation(
      summary = "List products",
      description = "Returns a page of products, with optional filtering by category, sorting and pagination"
  )
  fun getProductCatalog(
    @Parameter(
        description = "Filter by category",
        schema = Schema(
            type = "string",
            allowableValues = [
              "ELECTRONICS", "HOME_AND_KITCHEN", "CLOTHING",
              "ACCESSORIES", "SPORTS", "STATIONERY",
              "TOYS_AND_GAMES", "MUSICAL_INSTRUMENTS",
              "FOOTWEAR", "HOME_APPLIANCES"
            ]
        ),
        required = false
    )
    @RequestParam(name = "category", required = false)
    category: String?,

    @Parameter(description = "Sort field: SKU, PRICE, DESCRIPTION or CATEGORY")
    @RequestParam(name = "sortField", required = false)
    sortField: SortField?,

    @Parameter(description = "Sort order: ASC or DESC")
    @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC")
    sortOrder: SortOrder,

    @Parameter(description = "Page index (0-based)", example = "0")
    @RequestParam(name = "page", required = false, defaultValue = "0")
    page: Int,

    @Parameter(description = "Page size", example = "20")
    @RequestParam(name = "size", required = false, defaultValue = "20")
    size: Int

  ): ResponseEntity<GetProductCatalogResponse>
}
