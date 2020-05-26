package presentation

import data.api.github.GithubRepository
import data.files.FileTransfer
import data.files.IFileTransfer
import io.reactivex.schedulers.Schedulers


class MainFormPresenter {
    private val fileTransfer: IFileTransfer = FileTransfer()
    private val githubRepository = GithubRepository()

    fun onOkClicked() {

    }


    fun convertToStringFile(absolutePath: String) {
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
}