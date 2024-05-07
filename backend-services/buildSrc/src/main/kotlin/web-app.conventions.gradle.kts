plugins {
    id("kotlin-app.conventions")
    id("spring-app.conventions")
}

repositories { mavenCentral() }

group = "dev.hsuliz"

version = "0.0.1-SNAPSHOT"

dependencies { testImplementation("org.testcontainers:mongodb") }
