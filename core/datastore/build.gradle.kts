plugins {
    id("info.alihabibi.chortkeh.android.library")
}

android {
    namespace = "info.alihabibi.datastore"
}

dependencies {

    //Domain
    implementation(projects.core.domain)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

}