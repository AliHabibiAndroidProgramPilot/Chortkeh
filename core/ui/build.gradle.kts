plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.ui"
}

dependencies {

    // design system
    implementation(projects.core.designsystem)

    // common
    implementation(projects.core.common)

    // compose number wheel picker (time picker usage)
    implementation(libs.compose.number.picker)

}