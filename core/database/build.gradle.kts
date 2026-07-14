plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.room")
}

android {
    namespace = "info.alihabibi.database"
}

dependencies {

    // sqlite bundled / Rooms driver
    implementation(libs.androidx.sqlite)

    //Domain
    implementation(projects.core.domain)

    // common
    implementation(projects.core.common)

}