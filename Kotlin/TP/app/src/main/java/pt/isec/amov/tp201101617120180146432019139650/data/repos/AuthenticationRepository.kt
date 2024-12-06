package pt.isec.amov.tp201101617120180146432019139650.data.repos

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepository {

    private val auth by lazy { Firebase.auth }
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun createUserWithEmail(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                //if (result.isSuccessful) ... -> Aqui podemos fazer coisas/tratar melhor o result
                onResult(result.exception)
            }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                onResult(result.exception)
            }
    }

    fun signOut() {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }
}
