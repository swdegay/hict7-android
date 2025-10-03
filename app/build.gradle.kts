import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule

plugins {
    alias(libs.plugins.hict7.application)
    alias(libs.plugins.hict7.compose.application)
    alias(libs.plugins.hict7.hilt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "dev.sethdegay.hict7"

    aboutLibraries {
        library {
            duplicationMode = DuplicateMode.LINK
            duplicationRule = DuplicateRule.SIMPLE
        }
    }
}

dependencies {
    implementation(projects.core.commonRes)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)

    implementation(projects.feature.editor)
    implementation(projects.feature.home)
    implementation(projects.feature.settings)
    implementation(projects.feature.timer)

    implementation(libs.androidx.navigation3.lifecycle.viewmodel)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.kotlinx.serialization.core)

    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.compose.core)
    implementation(libs.aboutlibraries.compose.m3)
}