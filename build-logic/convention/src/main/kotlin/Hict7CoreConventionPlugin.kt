import com.android.build.gradle.LibraryExtension
import dev.sethdegay.buildlogic.SdkVersions
import dev.sethdegay.buildlogic.coreDependencies
import dev.sethdegay.buildlogic.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class Hict7CoreConventionPlugin : Plugin<Project> {

    companion object {
        private const val COMPILE_SDK = SdkVersions.CORE_COMPILE_SDK
        private const val TARGET_SDK = SdkVersions.CORE_TARGET_SDK
        private const val MIN_SDK = SdkVersions.CORE_MIN_SDK

        private val SOURCE_COMPATIBILITY = SdkVersions.PROJECT_SOURCE_COMPATIBILITY
        private val TARGET_COMPATIBILITY = SdkVersions.PROJECT_TARGET_COMPATIBILITY
        private val KOTLIN_JVM_TARGET = SdkVersions.PROJECT_JVM_TARGET
    }

    override fun apply(project: Project) {
        applyPlugins(project)
        applyAndroidConfig(project)
        applyKotlinConfig(project)
        applyDependencies(project)
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin(project.findPluginId("android-library"))
            plugin(project.findPluginId("kotlin-android"))
        }
    }

    private fun applyAndroidConfig(project: Project) {
        project.extensions.configure<LibraryExtension> {
            compileSdk = COMPILE_SDK
            defaultConfig.apply {
                targetSdk = TARGET_SDK
                minSdk = MIN_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions.apply {
                sourceCompatibility = SOURCE_COMPATIBILITY
                targetCompatibility = TARGET_COMPATIBILITY
            }
        }
    }

    private fun applyKotlinConfig(project: Project) {
        project.extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions.jvmTarget.set(KOTLIN_JVM_TARGET)
        }
    }

    private fun applyDependencies(project: Project) {
        project.coreDependencies()
    }
}