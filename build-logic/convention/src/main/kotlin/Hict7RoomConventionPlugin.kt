import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import dev.sethdegay.buildlogic.findPluginId
import dev.sethdegay.buildlogic.roomDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class Hict7RoomConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        applyRoomConfig(project)
        applyDependencies(project)
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin(project.findPluginId("room"))
            plugin(project.findPluginId("ksp"))
        }
    }

    private fun applyRoomConfig(project: Project) {
        project.extensions.configure<KspExtension> {
            arg("room.generateKotlin", "true")
        }
        project.extensions.configure<RoomExtension> {
            schemaDirectory("${project.projectDir}/schemas")
        }
    }

    private fun applyDependencies(project: Project) {
        project.roomDependencies()
    }
}