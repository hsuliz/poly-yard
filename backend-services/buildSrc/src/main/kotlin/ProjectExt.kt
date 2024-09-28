import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

// Workaround for https://github.com/gradle/gradle/issues/15383#issuecomment-1193076271
val Project.libs
    get() = the<org.gradle.accessors.dm.LibrariesForLibs>()
