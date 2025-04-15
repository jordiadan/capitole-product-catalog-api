package com.capitole.capitoleproductcatalogapi.infrastructure.helper

import java.io.File
import java.lang.System.setProperty
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait.forListeningPort
import org.testcontainers.containers.wait.strategy.Wait.forLogMessage
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import org.testcontainers.containers.wait.strategy.WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY

class DockerComposeHelper {

  companion object {
    private const val POSTGRES = "postgres"
    private const val POSTGRES_PORT = 5432

    fun create(): ComposeContainer {
      return ComposeContainer(File("docker-compose.yml"))
        .apply { withLocalCompose(true) }
        .apply {
          withExposedService(
            POSTGRES,
            POSTGRES_PORT,
            WaitAllStrategy(WITH_INDIVIDUAL_TIMEOUTS_ONLY)
              .apply { withStrategy(forListeningPort()) }
              .apply { withStrategy(forLogMessage(".*database system is ready to accept connections.*", 1)) })
        }
    }

    fun setSystemProperties(container: ComposeContainer) {
      val postgresHost = container.getServiceHost(POSTGRES, POSTGRES_PORT)
      val postgresPort = container.getServicePort(POSTGRES, POSTGRES_PORT)
      setProperty("database.host", postgresHost)
      setProperty("database.port", postgresPort.toString())
    }
  }
}
