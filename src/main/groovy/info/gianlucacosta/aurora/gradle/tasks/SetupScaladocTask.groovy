package info.gianlucacosta.aurora.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Prevents Scaladoc warnings by providing basic configuration
 */
class SetupScaladocTask extends DefaultTask {
    @TaskAction
    def setupScaladoc() {
        [
                "resources/main",
                "resources/generated",
                "resources/test"
        ].each { requestedDirName ->
            File requestedDir = new File(project.buildDir, requestedDirName)
            requestedDir.mkdirs()
        }
    }
}
