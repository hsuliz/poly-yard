plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(libs.bundles.spring.web)
    testImplementation(libs.bundles.spring.web.test)
}
