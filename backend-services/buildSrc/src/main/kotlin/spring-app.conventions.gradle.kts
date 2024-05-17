plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(libs.bundles.spring.web) { exclude(group = "ch.qos.logback") }
    testImplementation(libs.bundles.spring.web.test)
}
