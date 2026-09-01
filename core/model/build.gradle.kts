plugins {
    id("info.alihabibi.chortkeh.android.library")
}

android {
    namespace = "info.alihabibi.model"
}

dependencies {

    // domain
    implementation(projects.core.domain)

    // design system
    implementation(projects.core.designsystem)

    // common
    implementation(projects.core.common)

}