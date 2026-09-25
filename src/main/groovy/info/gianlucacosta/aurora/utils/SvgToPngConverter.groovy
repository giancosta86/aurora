package info.gianlucacosta.aurora.utils

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder

class SvgToPngConverter {
    public static void convert(File inputFile, File outputFile, float outputSize) {
        PNGTranscoder transcoder = new PNGTranscoder()

        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, outputSize)
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, outputSize)

        String sourceURL = inputFile.toURI().toString()
        TranscoderInput transcoderInput = new TranscoderInput(sourceURL)

        outputFile.withOutputStream { outputStream ->
            TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream)
            transcoder.transcode(transcoderInput, transcoderOutput)
        }
    }

    private SvgToPngConverter() {}
}
