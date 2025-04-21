package com.capitole.capitoleproductcatalogapi.infrastructure.testcases


import com.capitole.capitoleproductcatalogapi.Application
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import java.nio.charset.StandardCharsets

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
abstract class TestCase {

  @Autowired private lateinit var mvc: MockMvc

  protected lateinit var mapper: ObjectMapper

  @BeforeEach
  fun setUp() {
    mapper = ObjectMapper()
    mockMvc(mvc)
  }

  protected fun loadFixture(name: String): JsonNode =
      javaClass.classLoader
          .getResourceAsStream("responses/$name")
          ?.bufferedReader(StandardCharsets.UTF_8)
          ?.use { mapper.readTree(it) }
        ?: error("Fixture not found: responses/$name")
}
