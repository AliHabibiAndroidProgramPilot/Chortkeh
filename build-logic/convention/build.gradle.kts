
plugins {
    `kotlin-dsl`
}

group = "info.alihabibi.chortkeh.buildlogic"

dependencies {
    implementation(libs.kotlin.serializationPlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.composeCompilerPlugin)
}

gradlePlugin {
    plugins {

        register("androidApplication") {
            id = "info.alihabibi.chortkeh.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "info.alihabibi.chortkeh.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidCompose") {
            id = "info.alihabibi.chortkeh.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}