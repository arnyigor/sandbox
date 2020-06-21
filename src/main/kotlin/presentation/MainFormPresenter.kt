package presentation

import data.api.github.GithubRepository
import data.astro.AstroCalculator
import data.files.FileTransfer
import data.files.IFileTransfer
import io.reactivex.schedulers.Schedulers


class MainFormPresenter {
    private val fileTransfer: IFileTransfer = FileTransfer()
    private val astroCalculator: AstroCalculator = AstroCalculator()
    private val githubRepository = GithubRepository()

    fun onOkClicked() {

    }

    fun calcSun(text: String): String? {
        return try {
            val split = text.split(";")
            val date = split[0].toIntOrNull() ?: 1
            val lat = split[1].toDoubleOrNull() ?: 0.0
            val lon = split[2].toDoubleOrNull() ?: 0.0
            val localZone = split[3].toDoubleOrNull() ?: 0.0
            astroCalculator.calc(date, lat, lon, localZone)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
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
}