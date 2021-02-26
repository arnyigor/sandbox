package presentation.firebasefirestore

import com.google.gson.GsonBuilder
import data.firestore.FirestoreCredentials
import domain.firebase.FirestoreInteractor
import domain.firebase.FirestoreInteractorImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup

class FirebaseFirestorePresenter(var view: FirebaseFormView?) {
    private var firestoreInteractor: FirestoreInteractor? = null

    fun initFirestore(path: String) {
        firestoreInteractor = FirestoreInteractorImpl(FirestoreCredentials(path))
    }

    private fun formatData(map: Map<String, Any>): String? {
        return Jsoup.parse(GsonBuilder().setPrettyPrinting().create().toJson(map)).text()
    }

    fun loadCollectionData(collectionName: String) {
        if (collectionName.isNotBlank()) {
            firestoreInteractor?.let {
                Single.fromCallable { it.read(collectionName) }
                    .map {
                        StringBuilder().apply {
                            for (map in it) {
                                append("Collection:${formatData(map)}\n")
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
            view?.showError("Пустое поле Collection")
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
}