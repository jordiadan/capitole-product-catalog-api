package com.capitole.capitoleproductcatalogapi.infrastructure.testcases


import com.capitole.capitoleproductcatalogapi.Application
import io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
abstract class TestCase {

  @Autowired private lateinit var mvc: MockMvc

  @BeforeEach
  fun setUp() {
    mockMvc(mvc)
  }
}
