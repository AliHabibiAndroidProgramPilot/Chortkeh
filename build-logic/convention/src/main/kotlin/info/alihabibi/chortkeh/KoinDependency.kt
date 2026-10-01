package info.alihabibi.chortkeh

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureKoin() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("implementation", platform(libs.findLibrary("koin-bom").get()))
        add("implementation", libs.findLibrary("koin-core").get())
        add("implementation", libs.findLibrary("koin-android").get())
    }
}