package videoencoding

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import net.bramp.ffmpeg.builder.FFmpegBuilder.Verbosity
import java.io.File


class VideoEncoding(
    ffmpegPath: String,
    private val ffmpegForWin: Boolean = true,
) : IVideoEncoding {
    private val extent
        get() = if (ffmpegForWin) ".exe" else ""
    private val ffmpeg: FFmpeg = FFmpeg(ffmpegPath + File.separator + "ffmpeg$extent")
    private val ffprobe: FFprobe = FFprobe(ffmpegPath + File.separator + "ffprobe$extent")

    override fun mergeFiles(pathToListOfFiles: String, fileName: String): File {
        val substringBeforeLast = pathToListOfFiles.substringBeforeLast(File.separator)
        val doubleSeparator = File.separator + File.separator
        val newPath = substringBeforeLast.replace(File.separator, doubleSeparator) + doubleSeparator + "$fileName.mp4"
        val builder: FFmpegBuilder = FFmpegBuilder()
            .setInput(pathToListOfFiles)
            .setFormat("concat")
            .addExtraArgs("-safe")
            .addExtraArgs("0")
            .addOutput(newPath)
            .setAudioCodec("copy") // using the aac codec
            .setVideoCodec("copy") // Video using x264
            .done()
        builder.setVerbosity(Verbosity.DEBUG)
        val fFmpegExecutor = FFmpegExecutor(ffmpeg, ffprobe)
        fFmpegExecutor.createJob(builder).run()
        return File(newPath)
    }

    override fun getMediaData(path: String): String {
        val probeResult = ffprobe.probe(path)
        val format = probeResult.getFormat()
        val format1 = String.format(
            "%nFile: '%s' ; Format: '%s' ; Duration: %.3fs ; ",
            format.filename,
            format.format_long_name,
            format.duration
        )
        val stream = probeResult.getStreams()[0]
        val format2 = String.format(
            "%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
            stream.codec_long_name,
            stream.width,
            stream.height
        )
        return format1 + format2
    }
}