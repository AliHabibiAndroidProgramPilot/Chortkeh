plugins {
    id("info.alihabibi.chortkeh.android.library")
}

android {
    namespace = "info.alihabibi.sms"
}

dependencies {

    // domain
    implementation(projects.core.domain)

    // common
    implementation(projects.core.common)

    // design system
    implementation(projects.core.designsystem)

}