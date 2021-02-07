package domain.firebase

import data.firestore.FirestoreCredentials

class FirestoreInteractorImpl(
    private val firestoreCredentials: FirestoreCredentials
) : FirestoreInteractor {

    override fun read(collection: String): List<Map<String, Any>> {
        return firestoreCredentials.getDatabase()
            .collection(collection)
            .listDocuments().map {
                val snapshot = it.get().get()
                snapshot.data
            }.mapNotNull { it }
    }
}
