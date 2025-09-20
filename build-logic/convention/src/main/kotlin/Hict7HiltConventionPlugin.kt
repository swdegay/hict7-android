import dev.sethdegay.buildlogic.findPluginId
import dev.sethdegay.buildlogic.hiltDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class Hict7HiltConventionPlugin : Plugin<Project> {

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin(project.findPluginId("ksp"))
            plugin(project.findPluginId("hilt"))
        }
    }

    private fun applyDependencies(project: Project) {
        project.hiltDependencies()
    }

    override fun apply(project: Project) {
        applyPlugins(project)
        applyDependencies(project)
    }
}