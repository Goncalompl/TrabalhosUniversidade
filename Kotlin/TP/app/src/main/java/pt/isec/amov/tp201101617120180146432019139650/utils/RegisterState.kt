package pt.isec.amov.tp201101617120180146432019139650.utils

enum class RegisterState {
    IDLE, // Estado inicial
    LOADING, // Em processo de Registo
    SUCCESS, // Registo bem-sucedido
    ERROR // Erro no registo
}