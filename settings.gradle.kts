pluginManagement {
    repositories {
        /*google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }*/
//        gradlePluginPortal()
//        mavenCentral()
//        maven(url = "https://jitpack.io")
        maven(url = "https://maven.myket.ir")
    }

    includeBuild("build-logic")

    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        google()
//        mavenCentral()
//        maven(url = "https://jitpack.io")
        maven(url = "https://maven.myket.ir")
    }
}

rootProject.name = "Chortkeh"
include(":app")
include(":core:designsystem")
include(":features:onboarding")
include(":core:ui")
include(":core:datastore")
include(":core:domain")
include(":core:common")
include(":core:common_android")
include(":features:home")
include(":features:announcements")
include(":features:new_transaction")
include(":core:model")
include(":features:profile")
include(":core:database")
