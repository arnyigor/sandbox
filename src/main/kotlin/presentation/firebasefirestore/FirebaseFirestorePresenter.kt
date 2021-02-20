package presentation.firebasefirestore

import data.firestore.FirestoreCredentials
import domain.firebase.FirestoreInteractor
import domain.firebase.FirestoreInteractorImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FirebaseFirestorePresenter(var view: FirebaseFormView?) {
    private var firestoreInteractor: FirestoreInteractor? = null

    fun initFirestore(path: String) {
        firestoreInteractor = FirestoreInteractorImpl(FirestoreCredentials(path))
    }

    fun loadCollectionData(collectionName: String) {
        if (collectionName.isNotBlank()) {
            firestoreInteractor?.let {
                Single.fromCallable { it.read(collectionName) }
                    .map {
                        StringBuilder().apply {
                            for (map in it) {
                                append("Collection:$map\n")
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
}