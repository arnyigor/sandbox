package presentation.firebasefirestore

import com.google.gson.GsonBuilder
import data.firestore.FirestoreCredentials
import domain.firebase.FirestoreInteractor
import domain.firebase.FirestoreInteractorImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import utils.loadAppSettings
import utils.saveAppSettings

class FirebaseFirestorePresenter(var view: FirebaseFormView?) {
    @Volatile
    private var collectionData: List<Map<String, Any>> = emptyList()

    @Volatile
    private var documentData: Map<String, Any> = emptyMap()
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
        return GsonBuilder().setPrettyPrinting().create().toJson(map)
    }

    private fun loadCollections() {
        firestoreInteractor?.let {
            Single.fromCallable { it.readCollections() }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe { view?.setLoading(true) }
                .doFinally { view?.setLoading(false) }
                .subscribe(
                    { list -> view?.setCollections(list) },
                    { view?.showError(it.message) }
                )
        } ?: kotlin.run {
            view?.showError("Ошибка инициализации")
        }
    }

    @JvmOverloads
    fun loadCollectionData(collectionName: String, onlyIds: Boolean = false) {
        if (collectionName.isNotBlank()) {
            firestoreInteractor?.let {
                Single.fromCallable { it.readCollection(collectionName) }
                    .map { list ->
                        this.collectionData = list
                        collectionData to StringBuilder().apply {
                            if (onlyIds) {
                                list.forEach { map ->
                                    append("${map["id"]}->${map["host"]}\n")
                                }
                            } else {
                                for (map in list) {
                                    append("${formatData(map)}\n")
                                }
                            }
                        }.toString()
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .doOnSubscribe { view?.setLoading(true) }
                    .doFinally { view?.setLoading(false) }
                    .subscribe(
                        { pair ->
                            view?.setDocs(pair.first.map { map -> map["id"].toString() })
                            view?.setData(pair.second)
                        },
                        { view?.showError(it.message) }
                    )
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
        edtKeyText: String,
        edtValueText: String
    ) {
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
                                loadDocumentData(edtCollectionText, document)
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
        if(filepath.isNullOrBlank()){
            view?.showError("Пустой путь")
        }else{
            saveAppSettings(
                properties = listOf(
                    "filepath" to filepath
                )
            )
        }
    }

    fun loadSettings() {
        loadAppSettings(propertiesKeys = arrayOf("filepath"))["filepath"]?.let { path ->
            initFirestore(path)
        }
    }

    fun duplicateData(edtCollectionText: String, selected: Boolean, edtDocument: String) {
        when {
            edtCollectionText.isBlank() -> {
                view?.showError("Пустое поле Collection")
            }
            documentData.isEmpty() -> {
                view?.showError("Нет данных для дублирования")
            }
            else -> {
                val newDocument = documentData.toMutableMap()
                newDocument.remove("id")
                firestoreInteractor?.let { interactor ->
                    Single.fromCallable { interactor.addDocument(edtCollectionText, newDocument, edtDocument) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .doOnSubscribe { view?.setLoading(true) }
                        .doFinally { view?.setLoading(false) }
                        .subscribe({ success ->
                            if (success) {
                                view?.showSuccess("Документ продублирован")
                                loadCollectionData(edtCollectionText, selected)
                            } else {
                                view?.showError("Документ не продублирован")
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

    fun loadDocumentData(collection: String, docName: String) {
        firestoreInteractor?.let {
            Single.fromCallable { it.readDocument(collection, docName) }
                .map { map ->
                    this.documentData = map
                    documentData.keys.toList().sorted() to formatData(map)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe { view?.setLoading(true) }
                .doFinally { view?.setLoading(false) }
                .subscribe(
                    { pair ->
                        view?.setKeys(pair.first)
                        view?.setData(pair.second)
                    },
                    { view?.showError(it.message) }
                )
        } ?: kotlin.run {
            view?.showError("Ошибка инициализации")
        }
    }

    fun removeDocument(collection: String, edtDocumentText: String, selected: Boolean) {
        firestoreInteractor?.let {
            Single.fromCallable { it.removeDocument(collection, edtDocumentText) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe { view?.setLoading(true) }
                .doFinally { view?.setLoading(false) }
                .subscribe({ success ->
                    if (success) {
                        view?.showSuccess("Документ с id:$edtDocumentText удален")
                        loadCollectionData(collection, selected)
                    } else {
                        view?.showError("Документ с id:$edtDocumentText не удален")
                    }
                }, {
                    view?.showError(it.message)
                })
        } ?: kotlin.run {
            view?.showError("Ошибка инициализации")
        }
    }

    fun removeField(collection: String, documentText: String, key: String) {
        firestoreInteractor?.let {
            Single.fromCallable { it.deleteField(collection, documentText, key) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe { view?.setLoading(true) }
                .doFinally { view?.setLoading(false) }
                .subscribe({ success ->
                    if (success) {
                        view?.showSuccess("Поле $key удалено")
                        loadDocumentData(collection, documentText)
                    } else {
                        view?.showError("Поле $key не удалено")
                    }
                }, {
                    view?.showError(it.message)
                })
        } ?: kotlin.run {
            view?.showError("Ошибка инициализации")
        }
    }

    fun onKeyChanged(key: String) {
        view?.setKeyValue(documentData[key]?.toString())
    }
}