plugins {
    id("web-app.conventions")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security") {
        exclude(group = "ch.qos.logback")
    }
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")

    implementation("org.springframework.boot:spring-boot-starter") {
        exclude(group = "ch.qos.logback")
    }
    implementation("io.klogging:klogging-spring-boot-starter:0.5.13")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.mohamedrejeb.ksoup:ksoup-html:0.3.1")
}