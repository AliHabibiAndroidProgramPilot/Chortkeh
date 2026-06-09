plugins {
    id("info.alihabibi.chortkeh.android.application")
    id("info.alihabibi.chortkeh.android.compose")
}

android {

    namespace = "info.alihabibi.chortkeh"

    defaultConfig {
        applicationId = "info.alihabibi.chortkeh"
        testInstrumentationRunner =  "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {

    // Splash Screen
    implementation(libs.core.splashscreen)

    //design system
    implementation(projects.core.designsystem)

    // onboarding
    implementation(projects.features.onboarding)

    // otp
    implementation(projects.features.otp)

    // new transaction
    implementation(projects.features.newTransaction)

    // home
    implementation(projects.features.home)

    // announcements
    implementation(projects.features.announcements)

    // privacy and policy
    implementation(projects.features.privacyAndPolicy)

    // user account info
    implementation(projects.features.userAccountInfo)

    // ui
    implementation(projects.core.ui)

    // domain
    implementation(projects.core.domain)

    // datastore
    implementation(projects.core.datastore)

    // Unit Tests
    testImplementation(libs.junit)

    // Instrumented Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)

}