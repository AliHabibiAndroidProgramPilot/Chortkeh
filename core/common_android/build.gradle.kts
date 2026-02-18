plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.designsystem"
}

dependencies {

    // accompanist
    implementation(libs.accompanist.permissions)

    // components
    implementation(projects.core.ui)

}