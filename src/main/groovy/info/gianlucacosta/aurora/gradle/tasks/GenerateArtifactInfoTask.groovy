package info.gianlucacosta.aurora.gradle.tasks

import groovy.json.StringEscapeUtils
import info.gianlucacosta.aurora.gradle.AuroraException
import info.gianlucacosta.aurora.utils.Log
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

/**
 * Generates a class providing the information for the current artifact
 */
class GenerateArtifactInfoTask extends DefaultTask {
    @TaskAction
    def generateAppInfo() {
        if (!project.hasMoonLicense) {
            Log.info("Cannot generate artifact info, as MoonLicense is missing")

            throw new StopExecutionException()
        }


        String languageDirectoryName = project.mainLanguage
        Log.debug("Language directory name: ${languageDirectoryName}")

        String sourceExtension = languageDirectoryName
        String templateResourceName = "ArtifactInfo.${sourceExtension}.txt"


        String groupLastComponent = project.groupId.split("\\.").last()
        String artifactId = project.artifactId


        String sourcePackage

        if (groupLastComponent != artifactId) {
            String artifactIdPackageComponent =
                    artifactId
                            .replaceAll("[^A-Za-z0-9]", "_")
                            .replaceAll("__+", "_")

            sourcePackage = "${project.groupId}.${artifactIdPackageComponent}"
        } else {
            sourcePackage = project.groupId
        }

        Log.debug("Source package: ${sourcePackage}")


        String sourcePackageRelativePath = sourcePackage.replaceAll("\\.", "/")
        Log.debug("Source package relative path: ${sourcePackageRelativePath}")


        File sourcePackageDirectory = project.file("src/generated/${languageDirectoryName}/${sourcePackageRelativePath}")
        Log.debug("Source package directory: ${sourcePackageDirectory}")

        if (!sourcePackageDirectory.exists()) {
            if (!sourcePackageDirectory.mkdirs()) {
                throw new AuroraException("Cannot create source package directory: '${sourcePackageDirectory}'")
            }
        }


        String templateString = this.getClass().getResource(templateResourceName).text
        String sourceFileContent = injectVariables(templateString, sourcePackage)

        String sourceFileName = "ArtifactInfo.${sourceExtension}"
        File sourceFile = new File(sourcePackageDirectory, sourceFileName)

        Log.debug("Artifact Info source file: ${sourceFile}")

        sourceFile.text = sourceFileContent
    }


    private def injectVariables(String templateString, String sourcePackage) {
        return templateString
                .replace(
                "@PACKAGE@",
                sourcePackage
        )

                .replace(
                "@NAME@",
                StringEscapeUtils.escapeJava(project.moonLicense.productInfo.productName)
        )

                .replace(
                "@VERSION@",
                StringEscapeUtils.escapeJava(project.version.toString())
        )

                .replace(
                "@COPYRIGHT_YEARS@",
                StringEscapeUtils.escapeJava(project.moonLicense.getCopyrightYears())
        )

                .replace(
                "@COPYRIGHT_HOLDER@",
                StringEscapeUtils.escapeJava(project.moonLicense.productInfo.copyrightHolder)
        )

                .replace(
                "@LICENSE@",
                StringEscapeUtils.escapeJava(project.moonLicense.license.name)
        )

                .replace(
                "@WEBSITE@",
                StringEscapeUtils.escapeJava(project.url.toString())
        )

                .replace(
                "@FACEBOOK_PAGE@",
                project.facebookPage != null ?
                        "\"${StringEscapeUtils.escapeJava(project.facebookPage)}\""
                        :
                        "null"
        )

                .replace(
                "@RELEASE@",
                project.isRelease.toString()

        )
    }
}
