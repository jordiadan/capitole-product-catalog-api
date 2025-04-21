# Capitole Product Catalog API

A microservice exposing a product catalog with real‑time discount rules, filtering, sorting and pagination.  
Built in Kotlin/Spring Boot using a hexagonal architecture and DDD‐inspired boundaries, with comprehensive unit and
integration tests.

---

## 📝 Table of Contents

1. [Tech Stack](#-tech-stack)
2. [Getting Started](#-getting-started)
3. [Build & Run](#-build--run)
4. [Configuration](#-configuration)
5. [API Reference](#-api-reference)
6. [Architecture & Bounded Contexts](#-architecture--bounded-contexts)
7. [Discount Engine](#-discount-engine)
8. [Filtering, Sorting & Pagination](#-filtering-sorting--pagination)
9. [Error Handling](#-error-handling)
10. [Testing](#-testing)
11. [Next Steps](#-next-steps)

---

## 🔧 Tech Stack

- **Language:** Kotlin 1.9 (JVM 17)
- **Framework:** Spring Boot 3.4, Spring MVC
- **Database:** PostgreSQL 14 (+ Flyway migrations)
- **API Docs:** springdoc‑openapi (Swagger UI)
- **Testing:**
    - Unit: JUnit 5, Mockito
    - Controller: RestAssured(MockMvc)
    - Integration: Testcontainers + Docker Compose
- **Build:** Gradle 8.13

---

## 🚀 Getting Started

### Prerequisites

- JDK 17
- Docker & Docker Compose (for integration tests + Postgres)

### Clone & Build

```bash
git clone https://github.com/jordiadan/capitole-product-catalog-api.git
cd capitole-product-catalog-api
./gradlew clean build
```

### Run Locally

#### In‑memory (db)

```bash
./gradlew bootRun
```

Visit Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

---

## ⚙️ Configuration

All settings live in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/capitole
    username: postgres
    password: postgres
  flyway:
    locations: classpath:db/migration

discount:
  category-rules:
    ELECTRONICS: 15.0
    HOME_AND_KITCHEN: 25.0
```

Override in `src/integrationTest/resources/application-integration-test.yml` when running with
`-Dspring.profiles.active=integration-test`.

---

## 📖 API Reference

### List Products

```
GET /products
```

#### Query Parameters

| Name        | Type   | Description                                      |
|-------------|--------|--------------------------------------------------|
| `category`  | String | Filter by category (e.g. `ELECTRONICS`)          |
| `sortField` | Enum   | One of `SKU`, `PRICE`, `DESCRIPTION`, `CATEGORY` |
| `sortOrder` | Enum   | `ASC` or `DESC` (default `ASC`)                  |
| `page`      | Int    | Zero‑based page index (default `0`)              |
| `size`      | Int    | Page size (default `20`)                         |

#### Response Body

```json
{
  "products": [
    /* array of product DTOs */
  ],
  "page": 0,
  "size": 20,
  "totalElements": 30,
  "totalPages": 2
}
```

---

## 🏛 Architecture & Bounded Contexts

This service follows a **Hexagonal** (Ports & Adapters) architecture combined with DDD principles.

**Bounded Contexts**:

- **Product Context**: Manages core product entities, SKU, Description, Price, and Category value objects.
- **Discount Context**: Encapsulates discount rules and computation logic, decoupled from persistence and API.

**Read Model**:

- **Catalog**: A read‑optimized model projecting products with real‑time discounts for API consumption; outside
  transactional write contexts.

**Layers**:

- **Domain**: Entities, Value Objects, and Interfaces (`ProductRepository`, `DiscountService`).
- **Application**: Use‑cases (`GetProductCatalog`), DTO mapping, pagination logic.
- **Infrastructure**: Controllers, Repositories (JDBC & In‑memory), Swagger configuration, Exception Advice.

Loose coupling, dependency inversion, and single responsibility are applied throughout.

---

## 🔢 Discount Engine

- **`DiscountRule`** interface for encapsulating individual discount policies.
- **`SkuEndsWith5Rule`**: Applies a 30% discount to SKUs ending in “5”.
- **`CategoryRule`**: Defines a configurable discount percentage per product category.
- **`DefaultDiscountService`**: Iterates through all injected rules and selects the highest applicable discount.

---

## 🔍 Filtering, Sorting & Pagination

- **Filtering** and **sorting** delegated to `ProductSQLBuilder` (dynamic SQL construction).
- **Pagination** via `PageRequest.of(page,size)` and returned in a `Page<T>` wrapper.

---

## 🚨 Error Handling

- Invalid category → `CategoryNotFoundException`.
- Global handler (`@RestControllerAdvice`) → HTTP 400 + JSON error payload.

---

## ✅ Testing

- **Unit Tests**:
    - Domain: `PageRequest`, `SortSpec`, `Category.from()`, discount rules.
    - Application: `GetProductCatalogTest` (mocked repo & service).
    - Infrastructure: Controller and Exception Advice tests.
- **Integration Tests**:
    - `GetProductCatalogTestCase`: Boots full Spring context + Postgres, verifies JSON against fixtures.

Run all tests:
```bash
./gradlew clean check
```

---

## 🎯 Next Steps

- Add negative‑case tests (invalid sort, invalid pagination).
- CI/CD pipeline & Docker image.
- Consider moving discount definitions into a dedicated Promotion aggregate or database table for time‑bounded offers.

<p align="center">Built with ❤️ by Jordi Adán.</p>

