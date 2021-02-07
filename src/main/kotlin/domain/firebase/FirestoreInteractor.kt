package domain.firebase

interface FirestoreInteractor {
    fun read(collection: String): List<Map<String, Any>>
}
