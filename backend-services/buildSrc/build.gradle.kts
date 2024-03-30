val kotlin_version: String by project
val ktor_version: String by project
val logback_version: String by project

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin_version}")
    implementation("io.ktor.plugin:plugin:${ktor_version}")
    implementation("ch.qos.logback:logback-classic:${logback_version}")
}