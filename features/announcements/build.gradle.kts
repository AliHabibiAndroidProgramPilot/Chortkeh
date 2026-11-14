plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.announcements"
}

dependencies {

    // domain
    implementation(projects.core.domain)

    // model
    implementation(projects.core.model)

    // design system
    implementation(projects.core.designsystem)

    // ui
    implementation(projects.core.ui)

}