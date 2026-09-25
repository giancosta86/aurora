package info.gianlucacosta.aurora.gradle.tasks

import info.gianlucacosta.aurora.gradle.AuroraException
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Stops the build process if the project is not a release version
 */
class AssertReleaseTask extends DefaultTask {
    @TaskAction
    def assertRelease() {
        if (!project.isRelease) {
            throw new AuroraException("project.isRelease must be true")
        }
    }
}
