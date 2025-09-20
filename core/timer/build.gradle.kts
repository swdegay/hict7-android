plugins {
    alias(libs.plugins.hict7.core)
}

android {
    namespace = "dev.sethdegay.hict7.core.timer"
}

dependencies {
    api(projects.core.common)

    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
