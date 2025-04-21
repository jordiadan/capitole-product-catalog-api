package com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product

import com.capitole.capitoleproductcatalogapi.domain.pagination.Page
import com.capitole.capitoleproductcatalogapi.domain.pagination.PageRequest
import com.capitole.capitoleproductcatalogapi.domain.product.Category
import com.capitole.capitoleproductcatalogapi.domain.product.Description
import com.capitole.capitoleproductcatalogapi.domain.product.Price
import com.capitole.capitoleproductcatalogapi.domain.product.Product
import com.capitole.capitoleproductcatalogapi.domain.product.ProductRepository
import com.capitole.capitoleproductcatalogapi.domain.product.SKU
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortField
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortOrder
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec

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
    sortSpec: SortSpec?,
    pageRequest: PageRequest
  ): Page<Product> {
    val filtered = applyFilter(data, categoryFilter)
    val sorted = applySort(filtered, sortSpec)
    return applyPagination(sorted, pageRequest)
  }

  private fun applyFilter(products: List<Product>, category: Category?): List<Product> =
      category?.let { products.filter { it.category == category } } ?: products

  private fun applySort(products: List<Product>, sortSpec: SortSpec?): List<Product> {
    if (sortSpec == null) return products

    val comparator = when (sortSpec.field) {
      SortField.SKU -> compareBy<Product> { it.sku.value }
      SortField.PRICE -> compareBy { it.price.value }
      SortField.DESCRIPTION -> compareBy { it.description.value }
      SortField.CATEGORY -> compareBy { it.category.name }
    }

    return if (sortSpec.order == SortOrder.DESC) products.sortedWith(comparator.reversed())
    else products.sortedWith(comparator)
  }

  private fun applyPagination(products: List<Product>, pageRequest: PageRequest): Page<Product> {
    val total = products.size.toLong()
    val fromIndex = pageRequest.page * pageRequest.size
    val toIndex = (fromIndex + pageRequest.size).coerceAtMost(products.size)
    val pageContent = if (fromIndex >= products.size) emptyList() else products.subList(fromIndex, toIndex)
    return Page(
        content = pageContent,
        pageNumber = pageRequest.page,
        pageSize = pageRequest.size,
        totalElements = total
    )
  }
}
