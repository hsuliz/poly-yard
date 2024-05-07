plugins { `kotlin-dsl` }

repositories { gradlePluginPortal() }

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.kotlin.gradle)
    implementation(libs.plugin.kotlin.spring)
    implementation(libs.plugin.spring.boot.gradle)
    implementation(libs.plugin.spring.dependency.management)
}
