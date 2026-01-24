plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.otp"
}

dependencies {

    //common
    implementation(projects.core.common)

    // design system
    implementation(projects.core.designsystem)

}