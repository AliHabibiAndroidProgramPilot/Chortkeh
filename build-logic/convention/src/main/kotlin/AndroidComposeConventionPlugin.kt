import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.withPlugin("com.android.application") {
                configureCompose(libs)
            }
            pluginManager.withPlugin("com.android.library") {
                configureCompose(libs)
            }

        }
    }

    private fun Project.configureCompose(libs: org.gradle.api.artifacts.VersionCatalog) {

        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        val androidExtension = extensions.getByName("android") as CommonExtension<*, *, *, *, *, *>

        androidExtension.apply {
            buildFeatures {
                compose = true
            }
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("androidx-compose-ui").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("implementation", libs.findLibrary("androidx-compose-material3").get())

            add("implementation", libs.findLibrary("androidx-navigation-compose").get())

            add("implementation", libs.findLibrary("androidx-activity-compose").get())

            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        }
    }
}