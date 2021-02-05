package data.firestore

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream

class FirestoreCredentials(keyPath: String) {
    private var db: Firestore

    init {
        val serviceAccount = FileInputStream(keyPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
        db = FirestoreClient.getFirestore()
    }

    fun getDbCollectionDocuments(collection: String): List<MutableMap<String, Any>> {
        return db.collection(collection).listDocuments().map {
            val snapshot = it.get().get()
            snapshot.data
        }.mapNotNull { it }
    }
}
