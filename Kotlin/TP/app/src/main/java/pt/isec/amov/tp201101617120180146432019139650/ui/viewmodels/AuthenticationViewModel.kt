package pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import pt.isec.amov.tp201101617120180146432019139650.data.models.User
import pt.isec.amov.tp201101617120180146432019139650.data.repos.AuthenticationRepository


@Suppress("UNCHECKED_CAST")
class AuthenticationViewModelFactory(private val authenticationRepository: AuthenticationRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthenticationViewModel(authenticationRepository) as T
    }
}

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    private val _userEmail = mutableStateOf(authenticationRepository.currentUser?.email)
    val userEmail : MutableState<String?>
        get() = _userEmail

    private val _error = mutableStateOf<String?>(null)
    val error : MutableState<String?>
        get() = _error

    fun createUserWithEmail(email: String, password: String) {
        if (email.isBlank() || password.isBlank())
            return

        //vamos correr isto numa coroutine para não prender a thread principal
        //estamos a correla(a prende-la) ao view model (no scope do view model), para nao ser destruida quando viramos o ecrã por exemplo
        viewModelScope.launch {
            authenticationRepository.createUserWithEmail(email, password) { exception ->
                if (exception == null) //se a excecption for null, correu tudo bem, atulaizamos o user
                    _userEmail.value = authenticationRepository.currentUser?.email
                _error.value = exception?.message //aqui limpamos a exceccao, quer tenha corrido bem ou não
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isBlank() || password.isBlank())
            return
        viewModelScope.launch {
            authenticationRepository.signInWithEmail(email, password) { exception ->
                if (exception == null)
                    _userEmail.value = authenticationRepository.currentUser?.email
                _error.value = exception?.message
            }
        }
    }

    fun signOut() {
        authenticationRepository.signOut()
        _userEmail.value = null
        _error.value = null
    }
}