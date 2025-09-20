plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.compose.library)
}

android {
    namespace = "dev.sethdegay.hict7.core.designsystem"
}

dependencies {
    implementation(projects.core.commonRes)
    implementation(libs.androidx.compose.material.icons.extended)
}
