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
        maven { url = uri("https://srepo.tosantechno.net/repository/maven-group/") }
    }

    includeBuild("build-logic")

    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        google()
//        mavenCentral()
        maven(url = "https://maven.myket.ir")
        maven { url = uri("https://srepo.tosantechno.net/repository/maven-group/") }
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
include(":features:otp")
