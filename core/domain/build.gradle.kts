plugins {
    id("info.alihabibi.chortkeh.android.library")
}

android {
    namespace = "info.alihabibi.domain"
}

dependencies {
    // Kotlinx Flow
    implementation(libs.androidx.core.ktx)
}