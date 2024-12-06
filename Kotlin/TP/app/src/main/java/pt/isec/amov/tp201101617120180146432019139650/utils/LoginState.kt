package pt.isec.amov.tp201101617120180146432019139650.utils


enum class LoginState {
    IDLE, // Estado inicial
    LOADING, // Em processo de login
    SUCCESS, // Login bem-sucedido
    ERROR // Erro no login
}