package domain.firebase

interface FirestoreInteractor {
    fun readCollections(): List<String>
    fun readCollection(collection: String): List<Map<String, Any>>
    fun readDocument(collection: String, docName: String): Map<String, Any>
    fun setData(collection: String, documentName: String, data: Map<String, Any>): Boolean
    fun deleteField(collection: String, documentName: String, field: String): Boolean
    fun addDocument(collection: String, data: Map<String, Any>, edtDocument: String): Boolean
    fun removeDocument(collection: String, documentName: String): Boolean
    fun deleteDocument(collection: String, documentName: String): Boolean
}
