package domain.firebase

import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.SetOptions
import com.google.gson.Gson
import data.firestore.FirestoreCredentials


class FirestoreInteractorImpl(
    private val firestoreCredentials: FirestoreCredentials
) : FirestoreInteractor {

    override fun readCollections(): List<String> {
        return firestoreCredentials.getDatabase()
            .listCollections()
            .map { it.id }
    }

    override fun readCollection(collection: String): List<Map<String, Any>> {
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

    override fun readDocument(collection: String, docName: String): Map<String, Any> {
        return firestoreCredentials.getDatabase()
            .collection(collection)
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
                val stringValue = value.toString()
                if (stringValue.startsWith("{") && stringValue.endsWith("}")) {
                    sendMap[key] = Gson().fromJson(stringValue, HashMap::class.java)
                } else {
                    sendMap[key] = value
                }
            }
            val result = firestoreCredentials.getDatabase()
                .collection(collection)
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
            val result = firestoreCredentials.getDatabase()
                .collection(collection)
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

    override fun addDocument(collection: String, data: Map<String, Any>): Boolean {
        return try {
            val result = firestoreCredentials.getDatabase()
                .collection(collection)
                .document()
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
            val result = firestoreCredentials.getDatabase()
                .collection(collection)
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
