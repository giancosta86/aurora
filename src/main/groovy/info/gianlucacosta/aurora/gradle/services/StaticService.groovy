package info.gianlucacosta.aurora.gradle.services

import info.gianlucacosta.aurora.gradle.tasks.*
import info.gianlucacosta.aurora.utils.Log
import org.gradle.api.Project

/**
 * Invoked as soon as the plugin is applied - therefore, it contains
 * configuration-independent activities
 */
class StaticService {
    private final Project project

    StaticService(Project project) {
        this.project = project
    }


    def run() {
        setupPlugins()

        declareAuroraSettings()

        setupRepositories()

        createTasks()
    }


    private void setupPlugins() {
        project.plugins.apply("java")
    }


    private void declareAuroraSettings() {
        project.ext.auroraSettings = null
    }


    private void setupRepositories() {
        Log.info("Setting repositories...")

        project.repositories {
            mavenLocal()

            mavenCentral()
        }
    }


    private void createTasks() {
        Log.debug("Creating tasks...")

        project.tasks.create(name: "generateArtifactInfo", type: GenerateArtifactInfoTask)
        project.tasks.create(name: "generateAppDescriptor", type: GenerateAppDescriptorTask)
        project.tasks.create(name: "generateMainIcons", type: GenerateMainIconsTask)
        project.tasks.create(name: "generateDistIcons", type: GenerateDistIconsTask)
        project.tasks.create(name: "generatePom", type: GeneratePomTask)
        project.tasks.create(name: "generateCustomStartupScripts", type: GenerateCustomStartupScripts)
        project.tasks.create(name: "setupScaladoc", type: SetupScaladocTask)
    }
}
