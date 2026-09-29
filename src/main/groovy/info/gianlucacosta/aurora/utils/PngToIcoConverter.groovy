package info.gianlucacosta.aurora.utils

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import net.sf.image4j.codec.ico.ICOEncoder

import java.awt.image.BufferedImage

public class PngToIcoConverter {
    public static void convert(File inputFile, File outputFile) {
        inputFile.withInputStream { inputStream ->
            Image inputImage = new Image(inputStream)
            BufferedImage bufferedInputImage = SwingFXUtils.fromFXImage(inputImage, null)
            ICOEncoder.write(bufferedInputImage, outputFile)
        }
    }

    private PngToIcoConverter() {}
}
