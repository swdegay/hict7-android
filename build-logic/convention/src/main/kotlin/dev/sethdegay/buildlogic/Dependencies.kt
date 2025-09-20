package dev.sethdegay.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

private val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

private fun Project.findLibrary(alias: String): Provider<MinimalExternalModuleDependency> =
    libs.findLibrary(alias).get()

private fun Project.findPlugin(alias: String): Provider<PluginDependency> =
    libs.findPlugin(alias).get()

fun Project.findPluginId(alias: String): String = findPlugin(alias).get().pluginId

fun Project.findLibraryId(alias: String): String = findLibrary(alias).get().toString()

/*
 * Subjective groupings of dependencies used by app, core, and feature modules for easier management
 */

fun Project.coreDependencies() {
    dependencies {
        "implementation"(findLibrary("androidx-core-ktx"))
        "androidTestImplementation"(findLibrary("androidx-test-espresso-core"))
        "androidTestImplementation"(findLibrary("androidx-test-junit"))
        "testImplementation"(findLibrary("junit"))
    }
}

fun Project.coreCommonResDependencies() {
    dependencies {
        "implementation"(project(":core:common-res"))
    }
}

fun Project.composeDependencies() {
    dependencies {
        "implementation"(platform(findLibrary("androidx-compose-bom")))
        "implementation"(findLibrary("androidx-compose-ui"))
        "implementation"(findLibrary("androidx-compose-ui-graphics"))
        "implementation"(findLibrary("androidx-compose-ui-tooling-preview"))
        "implementation"(findLibrary("androidx-compose-material3"))
        "implementation"(findLibrary("androidx-activity-compose"))
        "debugImplementation"(findLibrary("androidx-compose-ui-tooling"))
        "debugImplementation"(findLibrary("androidx-compose-ui-test-manifest"))
        "androidTestImplementation"(platform(findLibrary("androidx-compose-bom")))
        "androidTestImplementation"(findLibrary("androidx-compose-ui-test-junit4"))
    }
}

fun Project.coreUiDependencies() {
    dependencies {
        @Suppress("SpellCheckingInspection")
        "implementation"(project(":core:designsystem"))
        "implementation"(project(":core:ui"))
    }
}

fun Project.hiltDependencies() {
    dependencies {
        "implementation"(findLibrary("hilt-android"))
        "ksp"(findLibrary("hilt-android-compiler"))
    }
}

fun Project.roomDependencies() {
    dependencies {
        "implementation"(findLibrary("androidx-room-runtime"))
        "ksp"(findLibrary("androidx-room-compiler"))
        "implementation"(findLibrary("androidx-room-ktx"))
        "testImplementation"(findLibrary("androidx-room-testing"))
    }
}

fun Project.viewModelDependencies() {
    dependencies {
        "implementation"(findLibrary("androidx-lifecycle-viewmodel-ktx"))
        "implementation"(findLibrary("androidx-lifecycle-viewmodel-compose"))
    }
}
