package info.gianlucacosta.aurora.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Generates a MoonDeploy app descriptor having sensible defaults
 */
class GenerateAppDescriptorTask extends DefaultTask {
    @TaskAction
    public void generateDescriptor() {
        ant.moonDeploy(
                baseURL: "${project.url}/releases/latest",

                name: project.moonLicense.productInfo.productName,
                version: project.version,

                description: project.description,
                publisher: project.auroraSettings.authors.first().name,

                skipPackageLevels: 1,

                iconPath: "mainIcon.png"
        ) {
            commandLine {
                param("bash")
                param("bin/${project.name}")
            }

            pkg(name: "${project.name}-${project.version}.zip")

            os(
                    name: "windows",
                    iconPath: "mainIcon.ico"
            ) {
                commandLine {
                    param("bin\\${project.name}.bat")
                }
            }
        }
    }
}
