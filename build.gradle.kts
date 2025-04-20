plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.4.4"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.capitole"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

repositories {
  mavenCentral()
}

sourceSets {
  create("integrationTest") {
    java.srcDir("src/integrationTest/java")
    resources.srcDir("src/integrationTest/resources")
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath
  }
}

val integrationTestImplementation by configurations.getting
val integrationTestRuntimeOnly by configurations.getting

dependencies {
  // Spring dependencies
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")

  // Database
  implementation("org.postgresql:postgresql:42.7.4")

  // Flyway
  implementation("org.flywaydb:flyway-core:11.0.0")
  implementation("org.flywaydb:flyway-database-postgresql:11.0.0")

  // Api docs
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

  implementation("org.jetbrains.kotlin:kotlin-reflect")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  integrationTestImplementation("org.awaitility:awaitility:4.2.1")
  integrationTestImplementation("org.testcontainers:junit-jupiter:1.20.0")
  integrationTestImplementation("io.rest-assured:spring-mock-mvc:5.5.1")
}

configurations {
  getByName("integrationTestImplementation") {
    extendsFrom(configurations.getByName("testImplementation"))
  }
  getByName("integrationTestRuntimeOnly") {
    extendsFrom(configurations.getByName("testRuntimeOnly"))
  }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.register<Test>("integrationTest") {
  description = "Execute integration tests."
  group = "verification"
  testClassesDirs = sourceSets["integrationTest"].output.classesDirs
  classpath = sourceSets["integrationTest"].runtimeClasspath
  shouldRunAfter(tasks.test)
}

tasks.check {
  dependsOn(tasks.named("integrationTest"))
}
