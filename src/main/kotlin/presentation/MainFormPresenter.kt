package presentation

import data.api.github.GithubRepository
import data.astro.AstroCalculator
import data.astro.TimeGeoCalc
import data.files.FileTransfer
import data.files.IFileTransfer
import data.network.ApiHelper
import data.network.DownloadResult
import domain.main.MainInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.apache.commons.io.FileUtils
import org.joda.time.DateTime
import utils.getPercent
import utils.isFileExist
import utils.launchAsync
import xls.CsvFileReader
import xls.MyFileReader
import java.io.File
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat


class MainFormPresenter {
    private var absolutePath: String? = null
    private var cookie: String? = null
    private var tsUrlWithoutEndIterate: String? = null
    private var start = 0
    private var end = 0
    private val fileTransfer: IFileTransfer = FileTransfer()
    private val astroCalculator: AstroCalculator = AstroCalculator()
    private val timeGeoCalc: TimeGeoCalc = TimeGeoCalc()
    private val githubRepository = GithubRepository()
    private val mainInteractor = MainInteractor()
    private val fileReader: MyFileReader = CsvFileReader()
    private val apiHelper: ApiHelper = ApiHelper()
    private var mainView: MainFormView? = null

    constructor()

    constructor(mainView: MainFormView?) : this() {
        this.mainView = mainView
    }

    fun onOkClicked() {
        mainInteractor.runTest()
    }

    fun calcSun(text: String): String? {
        return try {
            val split = text.split(";")
            val date = split[0].toIntOrNull() ?: 1
            val lat = split[1].toDoubleOrNull() ?: 0.0
            val lon = split[2].toDoubleOrNull() ?: 0.0
            val localZone = split[3].toDoubleOrNull() ?: 0.0
            astroCalculator.calc(DateTime.now().millis, lat, lon, localZone)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun timeGeoCalc(absolutePath: String) {
        timeGeoCalc.readFile(absolutePath)
    }

    fun convertFileToString(absolutePath: String) {
        fileTransfer.fileToString(absolutePath)
    }

    fun auth() {
        githubRepository.test()
                .observeOn(Schedulers.trampoline())
                .subscribe({
                    println("Result $it")
                }, {
                    it.printStackTrace()
                })
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

    fun downloadFiles(onProgress: (progress: Int) -> Unit, onSuccess: () -> Unit) {
        absolutePath?.let { path ->
            launchAsync({
                download(path, onProgress)
            }, {
                println("download files: OK")
                onSuccess()
            }, {
                println("download files error")
                it.printStackTrace()
            })
        }
    }

    fun filesList() {
        absolutePath?.let { path ->
            val listFile = File(path + File.separator + "mylist.txt")
            val sb = StringBuilder()
            File(path).walk().forEach {
                val name = it.absolutePath.substringAfterLast(File.separator)
                if (name.contains(".ts")) {
                    val doubleSeparator = File.separator + File.separator
                    val newPath = path.replace(File.separator, doubleSeparator) + doubleSeparator + name
                    val content = "file $newPath\n"
                    sb.append(content)
                }
            }
            if (listFile.isFileExist()) {
                listFile.writeText(sb.toString())
            }
        }
    }

    fun savePath(absolutePath: String, cookie: String, url: String, fromStr: String, toStr: String) {
        this.cookie = cookie
        this.tsUrlWithoutEndIterate = url
        this.start = fromStr.toIntOrNull() ?: 0
        this.end = toStr.toIntOrNull() ?: 0
        this.absolutePath = absolutePath
    }
}