plugins {
  id("web-app.conventions")
  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-amqp")
  implementation("org.springframework.security:spring-security-oauth2-jose")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

  testImplementation("org.springframework.security:spring-security-test")

  testImplementation("org.testcontainers:postgresql")
  testRuntimeOnly("org.postgresql:postgresql")
  //testRuntimeOnly("org.postgresql:r2dbc-postgresql")
  testRuntimeOnly("org.testcontainers:r2dbc")
  //testImplementation("org.springframework.amqp:spring-rabbit-test")
  testImplementation("org.testcontainers:rabbitmq:1.20.0")

  testImplementation("com.github.dasniko:testcontainers-keycloak:3.5.1")
}
