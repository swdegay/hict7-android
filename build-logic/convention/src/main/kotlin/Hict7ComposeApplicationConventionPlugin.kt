import com.android.build.api.dsl.ApplicationExtension
import dev.sethdegay.buildlogic.composeDependencies
import dev.sethdegay.buildlogic.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class Hict7ComposeApplicationConventionPlugin : Plugin<Project> {

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin(project.findPluginId("kotlin-compose"))
        }
    }

    private fun applyAndroidConfig(project: Project) {
        project.extensions.configure<ApplicationExtension> {
            buildFeatures.compose = true
        }
    }

    private fun applyDependencies(project: Project) {
        project.composeDependencies()
    }

    override fun apply(project: Project) {
        applyPlugins(project)
        applyAndroidConfig(project)
        applyDependencies(project)
    }
}