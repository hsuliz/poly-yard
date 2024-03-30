val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project


plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
    implementation("io.ktor.plugin:plugin:${ktorVersion}")
    implementation("ch.qos.logback:logback-classic:${logbackVersion}")
}