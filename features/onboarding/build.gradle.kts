plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.onboarding"
}

dependencies {

    // design system
    implementation(projects.core.designsystem)

    // ui
    implementation(projects.core.ui)

}