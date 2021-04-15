package presentation.mainform

import data.api.github.GithubRepository
import data.files.FileTransfer
import data.files.IFileTransfer
import data.firestore.FirestoreCredentials
import data.network.ApiHelper
import data.network.DownloadResult
import domain.files.FilesInteractor
import domain.firebase.FirestoreInteractor
import domain.firebase.FirestoreInteractorImpl
import domain.main.IMainInteractor
import domain.main.MainInteractor
import domain.main.TestType
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.apache.commons.io.FileUtils
import utils.*
import videoencoding.VideoEncoding
import xls.CsvFileReader
import xls.MyFileReader
import xls.XlsToDatabaseFileConverter
import java.io.File
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import kotlin.properties.Delegates

class MainFormPresenter(private val mainView: MainFormView) {
    var ffmpegForWin: Boolean = true
    private var listFile: File? = null
    private var absolutePath: String? = null
    private var cookie: String? = null
    private var tsUrlWithoutEndIterate: String? = null
    private var start = 0
    private var end = 0
    private val fileTransfer: IFileTransfer = FileTransfer()
    private val githubRepository = GithubRepository()
    private val filesInteractor = FilesInteractor()
    private val interactor: IMainInteractor = MainInteractor()
    private val fileReader: MyFileReader = CsvFileReader()
    private val apiHelper: ApiHelper = ApiHelper()
    var ffmpegPath: String? = null
    private var firestoreInteractor: FirestoreInteractor? = null
    private var loadingMode by Delegates.observable(false) { _, _, loading ->
        mainView.setUIEnabled(!loading)
    }

    fun onOkClicked() {
        interactor.runTest(TestType.KOTLIN)
    }

    fun convertFileToString(absolutePath: String) {
        fileTransfer.fileToString(absolutePath)
    }

    fun initFirestore(path: String) {
        firestoreInteractor = FirestoreInteractorImpl(FirestoreCredentials(path))
    }

    fun auth() {
        githubRepository.getGists()
    }

    fun convertStringToFile(absolutePath: String, result: String) {
        fileTransfer.stringToFile(absolutePath, result)
    }

    fun readXls(absolutePath: String) {
        Observable.fromCallable { fileReader.readXls(absolutePath) }
            .map { XlsToDatabaseFileConverter(it).convert() }
            .map {
                val outFile = File(absolutePath.substringBeforeLast("\\"), "res.txt")
                FileUtils.writeStringToFile(outFile, it, StandardCharsets.UTF_8)
                outFile
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.trampoline())
            .subscribe({
                println("On complete $it")
            }, {
                it.printStackTrace()
            })
    }

    private suspend fun download(absolutePath: String, onProgress: (progress: Int) -> Unit) {
        val url = tsUrlWithoutEndIterate
        val cookieData = cookie
        if (cookieData != null && url != null && start != 0 && end != 0 && end > start) {
            val headers = listOf(Pair("cookie", cookieData))
            val writefileName = url.substringAfterLast("/")
            (start..end).asFlow()
                .onEach {
                    onProgress(getPercent(it, end, start).toInt())
                }
                .flowOn(Dispatchers.Default)
                .flatMapMerge { i: Int ->
                    getSingleFile(
                        index = i,
                        headers = headers,
                        parentPath = absolutePath,
                        tsUrlWithoutEndIterate = url,
                        writefileName = writefileName
                    )
                }
                .flowOn(Dispatchers.IO)
                .catch { t -> t.printStackTrace() }
                .collect {
                    when (it) {
                        is DownloadResult.Progress -> {
                        }
                        is DownloadResult.Success -> {
//                                println("Result:${it.message}")
                        }
                        is DownloadResult.Error -> {
                            println("Error message:${it.message},cause:${it.cause?.stackTrace}")
                        }
                    }
                }
        }
    }

    private suspend fun getSingleFile(
        index: Int,
        headers: List<Pair<String, String>>,
        parentPath: String,
        tsUrlWithoutEndIterate: String,
        writefileName: String
    ): Flow<DownloadResult> {
        val formattedIndex = DecimalFormat("00000").format(index)
        val fileName = "$formattedIndex.ts"
        val url = "$tsUrlWithoutEndIterate$fileName"
        val file = File("$parentPath/$writefileName$fileName")
        return if (file.isFileExist()) {
            apiHelper.downloadFile(file, url, headers)
        } else {
            flow {
                emit(DownloadResult.Error("$file not exist"))
            }
        }
    }

    fun processFiles(
        fileName: String,
        onProgress: (progress: Int) -> Unit = {},
        onSuccess: (res: String) -> Unit = {},
        onError: (res: String?) -> Unit = {}
    ) {
        loadingMode = true
        absolutePath?.let { path ->
            launchAsync({
                download(path, onProgress)
                filesList()
                val mergeFiles = mergeFiles(fileName)
                removeFiles()
                val fileData = getFileData(mergeFiles.absolutePath)
                "file:$mergeFiles, size:${mergeFiles.formattedFileSize()}, $fileData"
            }, {
                loadingMode = false
                onSuccess(it)
            }, {
                loadingMode = false
                println("download files error")
                it.printStackTrace()
                onError(it.message)
            })
        }
    }

    private fun getFileData(resultFileName: String): String {
        return ffmpegPath?.let { path ->
            VideoEncoding(path, ffmpegForWin).getMediaData(resultFileName)
        } ?: throw Exception("ffmpegPath is null")
    }

    private fun mergeFiles(resultFileName: String): File {
        return ffmpegPath?.let { path ->
            val mergeFiles = VideoEncoding(path, ffmpegForWin).mergeFiles(
                listFile?.absolutePath!!,
                resultFileName.takeIf { it.isNotBlank() } ?: "result"
            )
            mergeFiles
        } ?: throw Exception("ffmpegPath is null")
    }

    fun filesList() {
        absolutePath?.let { path ->
            listFile = File(path + File.separator + "mylist.txt")
            val sb = StringBuilder()
            File(path).walk().forEach {
                val name = it.absolutePath.substringAfterLast(File.separator)
                if (name.contains(".ts")) {
                    val doubleSeparator = File.separator + File.separator
                    val newPath =
                        path.replace(File.separator, doubleSeparator) + doubleSeparator + name
                    val content = "file $newPath\n"
                    sb.append(content)
                }
            }
            if (listFile?.isFileExist() == true) {
                listFile?.writeText(sb.toString())
            }
        } ?: throw Exception("absolutePath is null")
    }

    private fun removeFiles() {
        absolutePath?.let { path ->
            File(path).walk().forEach {
                val name = it.absolutePath.substringAfterLast(File.separator)
                if (name.contains(".ts")) {
                    it.delete()
                }
            }
        } ?: throw Exception("absolutePath is null")
    }

    fun savePath(
        absolutePath: String,
        cookie: String,
        url: String,
        fromStr: String,
        toStr: String
    ) {
        this.cookie = cookie
        this.tsUrlWithoutEndIterate = url
        this.start = fromStr.toIntOrNull() ?: 0
        this.end = toStr.toIntOrNull() ?: 0
        this.absolutePath = absolutePath
    }

    fun readHtml(absolutePath: String) {
        val text = File(absolutePath).readText()
        val substringBefore = text.substringAfter("<tfoot>")
            .substringBefore("</tfoot>")
            .substringAfter("class=\"bar\">")
            .substringBefore("</td>")
        val split = substringBefore.split("of")
            .map { it.replace("\\s+".toRegex(), "") }
            .map { it.replace("NBSP".toRegex(), "") }
            .toList()
        split
    }

    fun saveData() {
        saveAppSettings(
            listOf(
                "absolutePath" to absolutePath,
                "cookie" to cookie,
                "start" to start.toString(),
                "end" to end.toString(),
                "url" to tsUrlWithoutEndIterate
            )
        )
    }

    fun loadData() {
        val settings = loadAppSettings(propertiesKeys = arrayOf("absolutePath", "cookie", "start", "end", "url"))
        absolutePath = settings["absolutePath"]
        cookie = settings["cookie"]
        start = settings["start"]?.toIntOrNull() ?: 0
        end = settings["end"]?.toIntOrNull() ?: 0
        tsUrlWithoutEndIterate = settings["url"]
        mainView.setData(absolutePath, cookie, start.toString(), end.toString(), tsUrlWithoutEndIterate)
    }
}