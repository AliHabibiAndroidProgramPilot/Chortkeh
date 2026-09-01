plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.channels"
}

dependencies {

    // design system
    implementation(projects.core.designsystem)

    // common
    implementation(projects.core.common)

    // ui
    implementation(projects.core.ui)

    // domain
    implementation(projects.core.domain)

    // model
    implementation(projects.core.model)

}