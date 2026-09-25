package info.gianlucacosta.aurora.gradle.tasks

import info.gianlucacosta.aurora.gradle.AuroraException
import info.gianlucacosta.aurora.utils.Log
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

/**
 * Stops the build process whenever the project:
 * <ol>
 *     <li>is in release mode (<b>project.isRelease</b> is true)</li>
 *     <li>is versioned with Git</li>
 *     <li>its Git status is not clean.</li>
 * </ol>
 */
class CheckGitTask extends DefaultTask {
    @TaskAction
    def checkGitStatus() {
        if (!project.isRelease) {
            Log.info("Not in release - skipping Git check")
            throw new StopExecutionException()
        }


        File gitFolder = project.file(".git")
        Log.debug("Expected Git folder: ${gitFolder.getAbsolutePath()}")

        if (!gitFolder.isDirectory()) {
            Log.info("Git folder not found - skipping Git check")
            throw new StopExecutionException()
        }

        def outputBuffer = new ByteArrayOutputStream()

        project.exec {
            workingDir project.projectDir
            executable = 'git'
            args = ["status", "--porcelain"]
            standardOutput = outputBuffer
        }

        String statusOutput = outputBuffer.toString()

        Log.debug("Git status output: ${statusOutput}")

        if (!statusOutput.isEmpty()) {
            throw new AuroraException("The project directory must be clean according to 'git status'")
        }
    }
}
