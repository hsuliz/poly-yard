val kotlin_version: String by project
val spring_version: String by project

plugins { `kotlin-dsl` }

repositories { gradlePluginPortal() }

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin_version}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${kotlin_version}")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${spring_version}")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.4")
}
