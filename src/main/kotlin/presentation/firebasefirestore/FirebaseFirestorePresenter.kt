package presentation.firebasefirestore

import com.google.gson.GsonBuilder
import data.firestore.FirestoreCredentials
import domain.firebase.FirestoreInteractor
import domain.firebase.FirestoreInteractorImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import utils.loadAppSettings
import utils.saveAppSettings

class FirebaseFirestorePresenter(var view: FirebaseFormView?) {
    @Volatile
    private var data: List<Map<String, Any>> = emptyList()
    private var firestoreInteractor: FirestoreInteractor? = null
    private var filepath: String? = null

    fun initFirestore(path: String) {
        filepath = path
        if (path.isNotBlank()) {
            firestoreInteractor = FirestoreInteractorImpl(FirestoreCredentials(path))
        }
        view?.setPathText(path)
        loadCollections()
    }

    private fun formatData(map: Map<String, Any>): String? {
        return Jsoup.parse(GsonBuilder().setPrettyPrinting().create().toJson(map)).text()
    }

    private fun loadCollections() {
        firestoreInteractor?.let {
            Single.fromCallable { it.readCollections() }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe { view?.setLoading(true) }
                .doFinally { view?.setLoading(false) }
                .subscribe(
                    { list -> view?.setData(list.joinToString()) },
                    { view?.showError(it.message) }
                )
        } ?: kotlin.run {
            view?.showError("Ошибка инициализации")
        }
    }

    fun loadCollectionData(collectionName: String) {
        if (collectionName.isNotBlank()) {
            firestoreInteractor?.let {
                Single.fromCallable { it.read(collectionName) }
                    .map { list ->
                        this.data = list
                        StringBuilder().apply {
                            for (map in list) {
                                append("${formatData(map)}\n")
                            }
                        }.toString()
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .doOnSubscribe { view?.setLoading(true) }
                    .doFinally { view?.setLoading(false) }
                    .subscribe({ view?.setData(it) }, {
                        view?.showError(it.message)
                    })
            } ?: kotlin.run {
                view?.showError("Ошибка инициализации")
            }
        } else {
            loadCollections()
        }
    }

    fun sendData(
        edtCollectionText: String,
        document: String,
        map: Map<String, Any>
    ) {
        when {
            edtCollectionText.isBlank() -> {
                view?.showError("Пустое поле Collection")
            }
            document.isBlank() -> {
                view?.showError("Пустое поле Document")
            }
            map.isEmpty() -> {
                view?.showError("Пустое поле значение")
            }
            else -> {
                firestoreInteractor?.let {
                    Single.fromCallable { it.setData(edtCollectionText, document, map) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .doOnSubscribe { view?.setLoading(true) }
                        .doFinally { view?.setLoading(false) }
                        .subscribe({ success ->
                            if (success) {
                                view?.showSuccess("Значения установлены")
                                loadCollectionData(edtCollectionText)
                            } else {
                                view?.showError("Значения не установлены")
                            }
                        }, {
                            view?.showError(it.message)
                        })
                } ?: kotlin.run {
                    view?.showError("Ошибка инициализации")
                }
            }
        }
    }

    fun sendData(edtCollectionText: String, document: String, edtKeyText: String, edtValueText: String) {
        when {
            edtCollectionText.isBlank() -> {
                view?.showError("Пустое поле Collection")
            }
            document.isBlank() -> {
                view?.showError("Пустое поле Document")
            }
            edtKeyText.isBlank() -> {
                view?.showError("Пустое поле ключ")
            }
            edtValueText.isBlank() -> {
                view?.showError("Пустое поле значение")
            }
            else -> {
                firestoreInteractor?.let {
                    Single.fromCallable { it.setData(edtCollectionText, document, mapOf(edtKeyText to edtValueText)) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .doOnSubscribe { view?.setLoading(true) }
                        .doFinally { view?.setLoading(false) }
                        .subscribe({ success ->
                            if (success) {
                                view?.showSuccess("Значения установлены")
                                loadCollectionData(edtCollectionText)
                            } else {
                                view?.showError("Значения не установлены")
                            }
                        }, {
                            view?.showError(it.message)
                        })
                } ?: kotlin.run {
                    view?.showError("Ошибка инициализации")
                }
            }
        }
    }

    fun savePathSettings() {
        saveAppSettings(
            properties = listOf(
                "filepath" to filepath
            )
        )
    }

    fun loadSettings() {
        loadAppSettings(propertiesKeys = arrayOf("filepath"))["filepath"]?.let { path ->
            initFirestore(path)
        }
    }

    fun duplicateData() {
        data
    }
}