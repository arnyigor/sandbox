package domain.firebase

import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.SetOptions
import com.google.gson.Gson
import data.firestore.FirestoreCredentials

class FirestoreInteractorImpl(path: String) : FirestoreInteractor {
    init {
        FirestoreCredentials.init(path)
    }

    private val database by lazy { requireNotNull(FirestoreCredentials.getDatabase()) }

    override fun readCollections(): List<String> {
        return database.listCollections()
            .map { it.id }
    }

    override fun readCollection(collection: String): List<Map<String, Any>> {
        return database.collection(collection)
            .listDocuments().map {
                val snapshot = it.get().get()
                val data = snapshot.data
                hashMapOf<String, Any>().apply {
                    data?.let { it1 -> putAll(it1) }
                    put("id", snapshot.id)
                }
            }
    }

    override fun readDocument(collection: String, docName: String): Map<String, Any> {
        return database.collection(collection)
            .document(docName)
            .let {
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
            val sendMap = mutableMapOf<String, Any>()
            for ((key, value) in data.entries) {
                val stringValue = value.toString().trim()
                if (stringValue.startsWith("{") && stringValue.endsWith("}")) {
                    sendMap[key] = Gson().fromJson(stringValue, HashMap::class.java)
                } else {
                    sendMap[key] = value
                }
            }
            val result = database.collection(collection)
                .document(documentName)
                .set(sendMap, SetOptions.merge())
                .get()
            println("Update time:${result.updateTime.toDate()}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun deleteField(collection: String, documentName: String, field: String): Boolean {
        return try {
            val result = database.collection(collection)
                .document(documentName)
                .update(field, FieldValue.delete())
                .get()
            println("Update time:${result.updateTime.toDate()}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun addDocument(collection: String, data: Map<String, Any>, edtDocument: String): Boolean {
        return try {
            val collectionReference = database.collection(collection)
            val documentReference = if (edtDocument.isNotBlank()) {
                collectionReference.document(edtDocument)
            } else {
                collectionReference.document()
            }
            val result = documentReference
                .set(data, SetOptions.merge())
                .get()
            println("Update time:${result.updateTime.toDate()}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun removeDocument(collection: String, documentName: String): Boolean {
        return try {
            val result = database.collection(collection)
                .document(documentName)
                .delete()
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
            val result = database.collection(collection).document(documentName).delete().get()
            println("Remove time:${result.updateTime.toDate()}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
