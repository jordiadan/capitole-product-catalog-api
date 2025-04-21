package com.capitole.capitoleproductcatalogapi.infrastructure.repositories.product

import com.capitole.capitoleproductcatalogapi.domain.pagination.Page
import com.capitole.capitoleproductcatalogapi.domain.pagination.PageRequest
import com.capitole.capitoleproductcatalogapi.domain.product.*
import com.capitole.capitoleproductcatalogapi.domain.product.sort.SortSpec
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PostgresSQLProductRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val sqlBuilder: ProductSQLBuilder
) : ProductRepository {

    override fun findAll(
        categoryFilter: Category?,
        sort: SortSpec?,
        pageRequest: PageRequest
    ): Page<Product> {
        val params = buildQueryParams(categoryFilter, sort, pageRequest)
        val contentSql = sqlBuilder.buildFindAllQuery(categoryFilter, sort)
        val content = jdbcTemplate.query(contentSql, params, productRowMapper)

        val countSql = sqlBuilder.buildCountQuery(categoryFilter)
        val totalElements = jdbcTemplate.queryForObject(countSql, params, Long::class.java) ?: 0L

        return Page(
            content = content,
            pageNumber = pageRequest.page,
            pageSize = pageRequest.size,
            totalElements = totalElements
        )
    }

    private fun buildQueryParams(
        category: Category?,
        sort: SortSpec?,
        pageRequest: PageRequest
    ): MapSqlParameterSource =
        MapSqlParameterSource().apply {
            category?.let { addValue("category", it.name) }
            sort?.let {
                addValue("sortField", it.field.name)
                addValue("sortOrder", it.order.name)
            }
            addValue("limit", pageRequest.size)
            addValue("offset", pageRequest.page * pageRequest.size)
        }

    private companion object {
        val productRowMapper = RowMapper<Product> { rs, _ ->
            Product(
                sku = SKU(rs.getString("sku")),
                description = Description(rs.getString("description")),
                price = Price(rs.getDouble("price")),
                category = Category.valueOf(rs.getString("category"))
            )
        }
    }
}
