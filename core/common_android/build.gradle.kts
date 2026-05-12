plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.common_android"
}

dependencies {

    // accompanist
    implementation(libs.accompanist.permissions)

    // components
    implementation(projects.core.ui)

    //design system
    implementation(projects.core.designsystem)

}