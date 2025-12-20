plugins {
    id("info.alihabibi.chortkeh.android.library")
}

android {
    namespace = "info.alihabibi.datastore"
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)

    //Domain
    implementation(projects.core.domain)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

}