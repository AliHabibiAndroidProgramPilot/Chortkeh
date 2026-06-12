plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.profile"
}

dependencies {

    // common android
    implementation(projects.core.commonAndroid)

    // common
    implementation(projects.core.common)

    // data store
    implementation(projects.core.datastore)

    // design system
    implementation(projects.core.designsystem)

    // domain
    implementation(projects.core.domain)

    // model
    implementation(projects.core.model)

    // ui
    implementation(projects.core.ui)

    // accompanist
    implementation(libs.accompanist.permissions)

}