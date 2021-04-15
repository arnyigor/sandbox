package data.firestore

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream

class FirestoreCredentials(keyPath: String) {
    private val db: Firestore

    init {
        val serviceAccount = FileInputStream(keyPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
        db = FirestoreClient.getFirestore()
    }

    fun getDatabase() = db
}
