plugins {
    alias(libs.plugins.hict7.feature)
    alias(libs.plugins.hict7.compose.library)
    alias(libs.plugins.hict7.hilt)
}

android {
    namespace = "dev.sethdegay.hict7.feature.settings"
}

dependencies {
    implementation(projects.core.data)
}