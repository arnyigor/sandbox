package domain.firebase

import com.google.cloud.firestore.SetOptions
import data.firestore.FirestoreCredentials

class FirestoreInteractorImpl(
    private val firestoreCredentials: FirestoreCredentials
) : FirestoreInteractor {

    override fun read(collection: String): List<Map<String, Any>> {
        return firestoreCredentials.getDatabase()
            .collection(collection)
            .listDocuments().map {
                val snapshot = it.get().get()
                val data = snapshot.data
                hashMapOf<String, Any>().apply {
                    data?.let { it1 -> putAll(it1) }
                    put("id", snapshot.id)
                }
            }
    }

    override fun setData(collection: String, documentName: String, data: Map<String, Any>): Boolean {
        return try {
            val result = firestoreCredentials.getDatabase()
                .collection(collection)
                .document(documentName)
                .set(data, SetOptions.merge())
                .get()
            println("Update time:${result.updateTime.toDate()}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun deleteDocument(collection: String, documentName: String): Boolean {
        return try {
            val result = firestoreCredentials.getDatabase()
                .collection(collection).document(documentName).delete().get()
            println("Remove time:${result.updateTime.toDate()}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
