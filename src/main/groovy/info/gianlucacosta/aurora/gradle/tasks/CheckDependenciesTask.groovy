package info.gianlucacosta.aurora.gradle.tasks

import info.gianlucacosta.aurora.gradle.AuroraException
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CheckDependenciesTask extends DefaultTask {
    @TaskAction
    def checkDependencies() {
        project.configurations.asList().each { configuration ->
            configuration.getAllDependencies().each { dependency ->
                if (dependency.hasProperty("version")) {
                    if (dependency.version != null && dependency.version.endsWith("-SNAPSHOT")) {
                        throw new AuroraException("You should not depend on a SNAPSHOT library: '${dependency.group}:${dependency.name}:${dependency.version}'")
                    }
                }
            }
        }
    }
}
