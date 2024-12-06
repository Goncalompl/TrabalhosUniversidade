package pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pt.isec.amov.tp201101617120180146432019139650.data.repos.ScoreRepository

class AddScoreViewModel(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    val score = mutableStateOf( 0 )
    val comment = mutableStateOf("")
    val imagePath: MutableState<String?> = mutableStateOf(null)

    fun saveScore() {
        scoreRepository.saveScore()
    }
}