package pt.isec.amov.tp201101617120180146432019139650.utils

import android.content.res.AssetManager
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.IOException
import java.io.InputStream

class FStorageUtil {
    companion object {
        fun addDataToFirestore(onResult: (Throwable?) -> Unit) {
            val db = Firebase.firestore

            //TODO ver modelo de dados
            val scores = hashMapOf(
                "nrgames" to 0,
                "topscore" to 0
            )
            db.collection("Scores").document("Level1").set(scores)
                .addOnCompleteListener { result ->
                    onResult(result.exception)
                }
        }

        fun updateDataInFirestore(onResult: (Throwable?) -> Unit) {
            val db = Firebase.firestore
            val v = db.collection("Scores").document("Level1")

            v.get(Source.SERVER)
                .addOnSuccessListener {
                    val exists = it.exists()
                    Log.i("Firestore", "updateDataInFirestore: Success? $exists")
                    if (!exists) {
                        onResult(Exception("Doesn't exist"))
                        return@addOnSuccessListener
                    }
                    //TODO ver modelo de dados
                    val value = it.getLong("nrgames") ?: 0
                    v.update("nrgames", value + 1)
                    onResult(null)
                }
                .addOnFailureListener { e ->
                    onResult(e)
                }
        }

        fun updateDataInFirestoreTrans(onResult: (Throwable?) -> Unit) {
            val db = Firebase.firestore
            val v = db.collection("Scores").document("Level1")

            db.runTransaction { transaction ->
                val doc = transaction.get(v)
                if (doc.exists()) {
                    //TODO ver modelo de dados
                    val newnrgames = (doc.getLong("nrgames") ?: 0) + 1
                    val newtopscore = (doc.getLong("topscore") ?: 0) + 100
                    transaction.update(v, "nrgames", newnrgames)
                    transaction.update(v, "topscore", newtopscore)
                    null
                } else
                    throw FirebaseFirestoreException(
                        "Doesn't exist",
                        FirebaseFirestoreException.Code.UNAVAILABLE
                    )
            }.addOnCompleteListener { result ->
                onResult(result.exception)
            }
        }

        fun removeDataFromFirestore(onResult: (Throwable?) -> Unit) {
            val db = Firebase.firestore
            //TODO ver modelo de dados
            val v = db.collection("Scores").document("Level1")

            v.delete()
                .addOnCompleteListener { onResult(it.exception) }
        }

        private var listenerRegistration: ListenerRegistration? = null

        fun startObserver(onNewValues: (Long, Long) -> Unit) {
            stopObserver()
            val db = Firebase.firestore
            //Manifesto interesse em alteraçoes num documento
            //TODO ver modelo de dados
            listenerRegistration = db.collection("Scores").document("Level1")
                .addSnapshotListener { docSS, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (docSS != null && docSS.exists()) {
                        val nrgames = docSS.getLong("nrgames") ?: 0
                        val topscore = docSS.getLong("topscore") ?: 0
                        Log.i("Firestore", "$nrgames : $topscore")
                        onNewValues(nrgames, topscore)
                    }
                }
        }

        fun stopObserver() {
            listenerRegistration?.remove()
        }

// Storage

        fun getFileFromAsset(assetManager: AssetManager, strName: String): InputStream? {
            var istr: InputStream? = null
            try {
                istr = assetManager.open(strName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return istr
        }

//https://firebase.google.com/docs/storage/android/upload-files

        fun uploadFile(inputStream: InputStream, imgFile: String) {
            val storage = Firebase.storage
            val ref1 = storage.reference
            val ref2 = ref1.child("images")
            val ref3 = ref2.child(imgFile)

            val uploadTask = ref3.putStream(inputStream)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref3.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    println(downloadUri.toString())
                } else {
                    // Handle failures
                    // ...
                }
            }

        }

    }
}