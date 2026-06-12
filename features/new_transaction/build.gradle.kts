plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.new_transaction"
}

dependencies {

    // design system
    implementation(projects.core.designsystem)

    // ui
    implementation(projects.core.ui)

    // domain
    implementation(projects.core.domain)

    // model
    implementation(projects.core.model)

    // common android
    implementation(projects.core.commonAndroid)

}