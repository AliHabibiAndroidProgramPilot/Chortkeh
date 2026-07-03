plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.room")
}

android {
    namespace = "info.alihabibi.database"
}

dependencies {

    //Domain
    implementation(projects.core.domain)

}