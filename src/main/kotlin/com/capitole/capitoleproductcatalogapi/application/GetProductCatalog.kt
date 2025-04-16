package com.capitole.capitoleproductcatalogapi.application

class GetProductCatalog {
  fun execute(): ProductCatalogDTO {
    // TODO Implement use case instead of using dummy content
    return ProductCatalogDTO(
        products = listOf(
            ProductCatalogDTO.ProductDTO(
                sku = "SKU0001",
                description = "Wireless Mouse with ergonomic design",
                price = "19.99",
                discountPercentage = "0",
                finalPrice = "19.99",
                category = "Electronics"
            ),
            ProductCatalogDTO.ProductDTO(
                sku = "SKU0005",
                description = "Noise-Cancelling Over-Ear Headphones",
                price = "120.00",
                discountPercentage = "30",
                finalPrice = "84.00",
                category = "Electronics"
            )
        )
    )
  }

}
