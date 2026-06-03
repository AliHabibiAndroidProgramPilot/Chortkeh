plugins {
    id("info.alihabibi.chortkeh.android.library")
    id("info.alihabibi.chortkeh.android.compose")
}

android {
    namespace = "info.alihabibi.user_account_info"
}

dependencies {

    // accompanist
    implementation(libs.accompanist.permissions)

    // components
    implementation(projects.core.ui)

    //design system
    implementation(projects.core.designsystem)

    // domain
    implementation(projects.core.domain)

    // common
    implementation(projects.core.common)

}