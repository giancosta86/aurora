package info.gianlucacosta.aurora.gradle.tasks

import info.gianlucacosta.aurora.gradle.AuroraPlugin
import info.gianlucacosta.aurora.gradle.settings.AuroraSettings
import info.gianlucacosta.aurora.gradle.settings.Author
import info.gianlucacosta.aurora.utils.Log
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Creates a Maven POM for the project
 */
class GeneratePomTask extends DefaultTask {
    @TaskAction
    def generatePom() {
        AuroraSettings auroraSettings = project.auroraSettings

        String pomPath = "${project.buildDir}/${AuroraPlugin.MAVEN_TEMP_DIRECTORY_NAME}/${project.artifactId}-${project.version}.pom"

        Log.info("Generating POM file: ${pomPath}")

        project.pom {
            project {
                name project.name
                description project.description

                artifactId project.artifactId

                url project.ext.url


                developers {
                    for (Author author : auroraSettings.authors) {
                        developer {
                            name author.name
                            email author.email

                            if (author.url) {
                                url author.url
                            }
                        }
                    }
                }

                scm {
                    url project.ext.url
                    connection "scm:git:git://github.com/${auroraSettings.gitHubUser}/${project.name}.git"
                    developerConnection "scm:git:git@github.com:${auroraSettings.gitHubUser}/${project.name}.git"
                }
            }
        }.writeTo(pomPath)
    }
}
