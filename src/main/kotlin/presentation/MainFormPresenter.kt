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


    private suspend fun download(absolutePath: String) {
        val headers = listOf(
            Pair(
                "cookie",
                "_ga=GA1.2.1060281196.1605350617; _gid=GA1.2.160345043.1605350617; _ym_uid=1605350617976242495; _ym_d=1605350617; _ym_isad=1; _fbp=fb.1.1605350618184.1971129977; CloudFront-Key-Pair-Id=APKAJH3GITHJTCN7K4VQ; _ym_visorc_64600876=w; CloudFront-Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vc3RyZWFtLWxpdmUuanVncnUub3JnLyoiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE2MzY4OTA5Nzl9LCJEYXRlR3JlYXRlclRoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTYwNTI2ODU3OX19fV19; CloudFront-Signature=aX0i49h0WjZBD17ig8WS6HMx4Ocy4Eg6n-XzubukzdzboyFhl4jV4LjaglRxFhueWFPF6lBRfnqgk1qXSXHSGdDPQLsql0l5o5A6JowCSP1y4OSRJEt4p--PnV-EItO9eY~B1h-~Y4V~iTK6E2Y2TnOpmLLSluizTpmVPiSR~PSqRg6Z3BooRGs0ZENrj1qAMmIPRCvDdwIbmX5bvwz-NZg6Ne1zQP-hRMEn~GgoGl~7zDYdRbec16BFx0nOinSZNgenNGW1S75ET9LiRAIaw88MlOuwzfBd0ro-5g~xo2BIhJxsGBmcSEG2ibdlIEoYR1taooELsczn4y7wbyX4Fw__"
            )
        )
        val start = 651
        val end = 2615
        val writefileName = "mobius20202-track4-day2_1920"
        val tsUrlWithoutEndIterate =
            "https://stream-live.jugru.org/mobius20202/mobius20202-track4-day2/mobius20202-track4-day2_1920/00000/mobius20202-track4-day2_1920_"
        (start..end).asFlow()
            .flatMapMerge { i: Int ->
                println("Progress:${getPercent(i, end)}")
                getSingleFile(
                    index = i,
                    headers = headers,
                    parentPath = absolutePath,
                    tsUrlWithoutEndIterate = tsUrlWithoutEndIterate,
                    writefileName = writefileName
                )
            }
            .flowOn(Dispatchers.IO)
            .catch { t ->
                t.printStackTrace()
            }
            .collect {
                when (it) {
                    is DownloadResult.Progress -> {
                    }
                    is DownloadResult.Success -> {
                        println("Result:${it.message}")
                    }
                    is DownloadResult.Error -> {
                        println("Error:${it.message}")
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

    fun downloadFiles(absolutePath: String) {
        launchAsync({
            download(absolutePath)
        }, {
            println("download files: OK")
        }, {
            println("download files error")
            it.printStackTrace()
        })
    }
}