package info.gianlucacosta.aurora.gradle

import info.gianlucacosta.aurora.gradle.services.DynamicService
import info.gianlucacosta.aurora.gradle.services.StaticService
import info.gianlucacosta.aurora.gradle.settings.AuroraSettings
import info.gianlucacosta.aurora.utils.Log
import org.gradle.BuildAdapter
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

/**
 * Aurora's plugin for Gradle
 */
class AuroraPlugin implements Plugin<Project> {
    public static final String MAVEN_TEMP_DIRECTORY_NAME = "mavenTemp"

    @Override
    void apply(Project project) {
        Log.debug("Running the static service...")
        StaticService staticService = new StaticService(project)
        staticService.run()


        project.ext.aurora = { Closure closure ->
            AuroraSettings auroraSettings = new AuroraSettings()

            closure.delegate = auroraSettings
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            closure()

            Log.debug("Aurora settings: ${auroraSettings.dump()}")

            project.ext.auroraSettings = auroraSettings

            DynamicService dynamicService = new DynamicService(project)
            dynamicService.run()
        }


        project.gradle.addBuildListener(new BuildAdapter() {
            @Override
            void projectsEvaluated(Gradle gradle) {
                ensureAuroraSettings()
            }

            private void ensureAuroraSettings() {
                if (project.auroraSettings == null) {
                    throw new AuroraException("You need to employ the aurora{} block within the build script")
                }

                Log.debug("Aurora settings are available")
            }
        })
    }
}
