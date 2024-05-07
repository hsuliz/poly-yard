import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
}

dependencies {
    implementation(libs.bundles.kotlin.core)
    implementation(libs.bundles.kotlin.core.test)
}

java { sourceCompatibility = JavaVersion.VERSION_21 }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging { events("passed") }
}
