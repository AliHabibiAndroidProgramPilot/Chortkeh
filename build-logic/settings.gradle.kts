@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
//        google()
//        mavenCentral()
        maven(url = "https://maven.myket.ir")
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }

}

rootProject.name = "build-logic"

include(":convention")