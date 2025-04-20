package com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product

import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.SortOrder

class InMemoryProductRepository : ProductRepository {

  private val data = listOf(
      Product(SKU("SKU0001"), Description("Wireless Mouse with ergonomic design"), Price(19.99), Category.ELECTRONICS),
      Product(SKU("SKU0002"), Description("4K Ultra HD Smart TV, 55 inches"), Price(499.00), Category.ELECTRONICS),
      Product(SKU("SKU0003"), Description("Stainless Steel Water Bottle, 1L"), Price(29.50), Category.HOME_AND_KITCHEN),
      Product(SKU("SKU0004"), Description("Cotton T-Shirt, Unisex, Size M"), Price(15.00), Category.CLOTHING),
      Product(SKU("SKU0005"), Description("Noise-Cancelling Over-Ear Headphones"), Price(120.00), Category.ELECTRONICS),
      Product(SKU("SKU0006"), Description("USB-C to USB Adapter"), Price(9.99), Category.ELECTRONICS),
      Product(SKU("SKU0007"), Description("Leather Wallet with RFID Protection"), Price(75.00), Category.ACCESSORIES),
      Product(SKU("SKU0008"), Description("Yoga Mat with Non-Slip Surface"), Price(35.00), Category.SPORTS),
      Product(SKU("SKU0009"), Description("Smartwatch with Heart Rate Monitor"), Price(220.00), Category.ELECTRONICS),
      Product(SKU("SKU0010"), Description("Ceramic Coffee Mug, 350ml"), Price(12.50), Category.HOME_AND_KITCHEN),
      Product(SKU("SKU0011"), Description("Bluetooth Portable Speaker"), Price(60.00), Category.ELECTRONICS),
      Product(SKU("SKU0012"), Description("Backpack with Laptop Compartment"), Price(85.00), Category.ACCESSORIES),
      Product(
          SKU("SKU0013"),
          Description("Stainless Steel Cutlery Set, 24 Pieces"),
          Price(18.00),
          Category.HOME_AND_KITCHEN
      ),
      Product(SKU("SKU0014"), Description("Electric Guitar Starter Pack"), Price(250.00), Category.MUSICAL_INSTRUMENTS),
      Product(SKU("SKU0015"), Description("Running Shoes, Men's Size 42"), Price(42.00), Category.FOOTWEAR),
      Product(
          SKU("SKU0016"),
          Description("Digital Bathroom Scale with Body Fat Analyzer"),
          Price(27.99),
          Category.HOME_APPLIANCES
      ),
      Product(SKU("SKU0017"), Description("Set of 6 Organic Cotton Socks"), Price(14.99), Category.CLOTHING),
      Product(SKU("SKU0018"), Description("DSLR Camera with 18-55mm Lens"), Price(300.00), Category.ELECTRONICS),
      Product(SKU("SKU0019"), Description("Hardcover Notebook, A5, 200 Pages"), Price(8.99), Category.STATIONERY),
      Product(SKU("SKU0020"), Description("Microwave Oven, 20L Capacity"), Price(65.00), Category.HOME_APPLIANCES),
      Product(
          SKU("SKU0021"),
          Description("LED Desk Lamp with Adjustable Brightness"),
          Price(23.50),
          Category.HOME_AND_KITCHEN
      ),
      Product(SKU("SKU0022"), Description("Wireless Charger Pad for Smartphones"), Price(19.00), Category.ELECTRONICS),
      Product(
          SKU("SKU0023"),
          Description("Men's Quartz Analog Watch with Leather Strap"),
          Price(55.00),
          Category.ACCESSORIES
      ),
      Product(
          SKU("SKU0024"),
          Description("Wooden Chess Set with Folding Board"),
          Price(30.00),
          Category.TOYS_AND_GAMES
      ),
      Product(
          SKU("SKU0025"),
          Description("Home Security Camera with Night Vision"),
          Price(99.00),
          Category.ELECTRONICS
      ),
      Product(
          SKU("SKU0026"),
          Description("Aromatherapy Essential Oil Diffuser"),
          Price(16.50),
          Category.HOME_AND_KITCHEN
      ),
      Product(SKU("SKU0027"), Description("Professional Blender with 2L Jar"), Price(40.00), Category.HOME_APPLIANCES),
      Product(SKU("SKU0028"), Description("Kids' Educational Tablet Toy"), Price(22.00), Category.TOYS_AND_GAMES),
      Product(
          SKU("SKU0029"),
          Description("Mechanical Gaming Keyboard with RGB Lighting"),
          Price(110.00),
          Category.ELECTRONICS
      ),
      Product(SKU("SKU0030"), Description("Pack of 10 Ballpoint Pens, Blue Ink"), Price(7.50), Category.STATIONERY)
  )

  override fun findAll(
    categoryFilter: Category?,
    sortField: SortField?,
    sortOrder: SortOrder
  ): List<Product> {
    var result = data

    categoryFilter?.let {
      result = result.filter { p -> p.category == it }
    }

    sortField?.let { field ->
      result = when (field) {
        SortField.SKU -> result.sortedByOrNull { it.sku.value }
        SortField.PRICE -> result.sortedByOrNull { it.price.value }
        SortField.DESCRIPTION -> result.sortedByOrNull { it.description.value }
        SortField.CATEGORY -> result.sortedByOrNull { it.category.name }
      }.let { if (sortOrder == SortOrder.DESC) it.reversed() else it }
    }

    return result
  }

  private fun <T : Comparable<T>> List<Product>.sortedByOrNull(selector: (Product) -> T) =
      if (this.isEmpty()) this else this.sortedBy(selector)
}
