pluginManagement {
    repositories {
        /*google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()*/
        maven(url = "https://maven.myket.ir")
    }

    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        google()
//        mavenCentral()
        maven(url = "https://maven.myket.ir")
    }
}

rootProject.name = "Chortkeh"
include(":app")
include(":core:designsystem")
