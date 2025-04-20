package com.capitole.capitoleproductcatalogapi.infrastructure.advice

import com.capitole.capitoleproductcatalogapi.domain.product.CategoryNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(CategoryNotFoundException::class)
  fun handleInvalidCategory(ex: CategoryNotFoundException): ResponseEntity<ErrorResponse> {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse(400, ex.message!!))
  }
}
