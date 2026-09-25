package info.gianlucacosta.aurora.gradle.tasks

import info.gianlucacosta.aurora.utils.Log
import info.gianlucacosta.aurora.utils.PngToIcoConverter
import info.gianlucacosta.aurora.utils.SvgToPngConverter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

/**
 * Generates icons (a PNG and an ICO) packaged into the archives created by the "application" plugin
 */
class GenerateDistIconsTask extends DefaultTask {
    @TaskAction
    def generateIcons() {
        File svgSourceFile = project.file("mainIcon.svg")

        if (!svgSourceFile.exists()) {
            throw new StopExecutionException()
        }

        File distDirectory = project.file("src/generated/dist")
        distDirectory.mkdirs()

        File pngIconFile = new File(distDirectory, "mainIcon.png")

        Log.debug("Source SVG icon file: ${svgSourceFile.getAbsolutePath()}")
        Log.debug("Target PNG icon file: ${pngIconFile.getAbsolutePath()}")

        SvgToPngConverter.convert(svgSourceFile, pngIconFile, 64)

        File icoIconFile = new File(distDirectory, "mainIcon.ico")

        Log.debug("Source PNG icon file: ${pngIconFile.getAbsolutePath()}")
        Log.debug("Target ICO icon file: ${icoIconFile.getAbsolutePath()}")

        PngToIcoConverter.convert(pngIconFile, icoIconFile)
    }
}
