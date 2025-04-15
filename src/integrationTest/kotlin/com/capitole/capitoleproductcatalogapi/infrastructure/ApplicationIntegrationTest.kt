package com.capitole.capitoleproductcatalogapi.infrastructure

import com.capitole.capitoleproductcatalogapi.infrastructure.helper.DockerComposeHelper
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ApplicationIntegrationTest {

  companion object {
    @Container val dockerComposeContainer = DockerComposeHelper.create()

    @BeforeAll
    @JvmStatic
    fun setSystemProperties() {
      DockerComposeHelper.setSystemProperties(dockerComposeContainer)
    }
  }
}
