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

    // design system
    implementation(projects.core.designsystem)

    // common android
    implementation(projects.core.commonAndroid)

    // onboarding
    implementation(projects.features.onboarding)

    // new transaction
    implementation(projects.features.newTransaction)

    // home
    implementation(projects.features.home)

    // announcements
    implementation(projects.features.announcements)

    // ui
    implementation(projects.core.ui)

    // domain
    implementation(projects.core.domain)

    // datastore
    implementation(projects.core.datastore)

    // profile
    implementation(projects.features.profile)

    // Unit Tests
    testImplementation(libs.junit)

    // Instrumented Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)

}