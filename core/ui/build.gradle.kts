plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.compose.library)
}

android {
    namespace = "dev.sethdegay.hict7.core.ui"
}

dependencies {
    api(projects.core.commonRes)
    api(projects.core.designsystem)
    api(projects.core.model)
    implementation(projects.core.timer) // CountdownManager.State

    implementation(libs.androidx.compose.material3.window.size.clazz)
    implementation(libs.calendar.compose)
}