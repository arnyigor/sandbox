package presentation.videoencoding

import kotlinx.coroutines.Job
import utils.formattedFileSize
import utils.launchAsync
import videoencoding.IVideoEncoding
import videoencoding.VideoEncoding
import java.io.*
import java.util.*
import kotlin.properties.Delegates


class VideoEncodingPresenter(private val view: VideoEncodingView) {
    private var job: Job? = null
    private var loadingMode by Delegates.observable(false) { _, _, loading ->
        view.setUIEnabled(!loading)
        view.setButton3Title(if (loading) "Cancel" else "Merge")
    }
    private lateinit var videEncoding: IVideoEncoding
    private var ffmpegPath: String? = null
    private var filesPath: String? = null

    fun onInitUI() {
        loadSettings()
        view.setLabel1Text("ffmpeg path:$ffmpegPath")
        view.setLabel2Text("filesPath path:$filesPath")
    }

    private fun saveSettings() {
        val configFile = File("config.properties")
        try {
            val props = Properties()
            props.setProperty("ffmpegPath", ffmpegPath)
            props.setProperty("filesPath", filesPath)
            val writer = FileWriter(configFile)
            props.store(writer, "video settings")
            writer.close()
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    private fun loadSettings() {
        val configFile = File("config.properties")
        try {
            val reader = FileReader(configFile)
            val props = Properties()
            props.load(reader)
            ffmpegPath = props.getProperty("ffmpegPath")
            filesPath = props.getProperty("filesPath")
            reader.close()
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun ffmpegPath(ffmpegPath: String) {
        this.ffmpegPath = ffmpegPath
        view.setLabel1Text("ffmpeg path:$ffmpegPath")
    }

    fun filesPath(filesPath: String) {
        this.filesPath = filesPath
        view.setLabel2Text("filesPath path:$filesPath")
    }

    fun merge() {
        saveSettings()
        loadingMode = true
        job = launchAsync({
            videEncoding = VideoEncoding(ffmpegPath!!)
            videEncoding.mergeFiles(filesPath!!, "result")
        }, {
            loadingMode = false
            val formattedFileSize = it.formattedFileSize(3)
            view.showResult("file:$it,size:$formattedFileSize")
        }, {
            loadingMode = false
            view.showError(it.message)
        }, {
            loadingMode = false
            it.printStackTrace()
        })
    }

}