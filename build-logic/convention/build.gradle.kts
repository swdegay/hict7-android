import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "dev.sethdegay.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.room.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("hict7Application") {
            id = libs.plugins.hict7.application.get().pluginId
            implementationClass = "Hict7ApplicationConventionPlugin"
        }
        register("hict7ComposeApplication") {
            id = libs.plugins.hict7.compose.application.get().pluginId
            implementationClass = "Hict7ComposeApplicationConventionPlugin"
        }
        register("hict7ComposeLibrary") {
            id = libs.plugins.hict7.compose.library.get().pluginId
            implementationClass = "Hict7ComposeLibraryConventionPlugin"
        }
        register("hict7Core") {
            id = libs.plugins.hict7.core.get().pluginId
            implementationClass = "Hict7CoreConventionPlugin"
        }
        register("hict7Feature") {
            id = libs.plugins.hict7.feature.get().pluginId
            implementationClass = "Hict7FeatureConventionPlugin"
        }
        register("hict7Hilt") {
            id = libs.plugins.hict7.hilt.get().pluginId
            implementationClass = "Hict7HiltConventionPlugin"
        }
        register("hict7Room") {
            id = libs.plugins.hict7.room.get().pluginId
            implementationClass = "Hict7RoomConventionPlugin"
        }
    }
}
