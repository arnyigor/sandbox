package domain.firebase

interface FirestoreInteractor {
    fun read(collection: String): List<Map<String, Any>>
    fun setData(collection: String, documentName: String, data: Map<String, Any>): Boolean
    fun deleteDocument(collection: String, documentName: String): Boolean
}
