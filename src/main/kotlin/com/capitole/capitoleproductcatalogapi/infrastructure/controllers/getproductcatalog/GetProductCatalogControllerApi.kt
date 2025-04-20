package com.capitole.capitoleproductcatalogapi.infrastructure.controllers.getproductcatalog

import com.capitole.capitoleproductcatalogapi.domain.product.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.SortOrder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Products", description = "Retrieve, filter & sort product catalog")
interface ProductCatalogApi {

  @Operation(
      summary = "List products",
      description = "Returns all products, with optional filtering by category and sorting"
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
    @RequestParam("category", required = false)
    category: String?,

    @Parameter(description = "Sort field: SKU, PRICE, DESCRIPTION or CATEGORY")
    @RequestParam("sortField", required = false)
    sortField: SortField?,

    @Parameter(description = "Sort order: ASC or DESC")
    @RequestParam("sortOrder", required = false, defaultValue = "ASC")
    sortOrder: SortOrder
  ): ResponseEntity<GetProductCatalogResponse>
}
