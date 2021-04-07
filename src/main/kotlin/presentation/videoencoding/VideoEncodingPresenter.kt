package presentation.videoencoding

import kotlinx.coroutines.Job
import utils.formattedFileSize
import utils.launchAsync
import utils.loadAppSettings
import utils.saveAppSettings
import videoencoding.IVideoEncoding
import videoencoding.VideoEncoding
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
        val settings = loadAppSettings("ffmpegPath", "filesPath")
        ffmpegPath = settings[ffmpegPath]
        ffmpegPath = settings[filesPath]
        view.setLabel1Text("ffmpeg path:$ffmpegPath")
        view.setLabel2Text("filesPath path:$filesPath")
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
        saveAppSettings(
            properties = listOf(
                "ffmpegPath" to ffmpegPath,
                "filesPath" to filesPath
            ),
            comments = "video settings"
        )
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