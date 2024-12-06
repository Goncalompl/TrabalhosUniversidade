package pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.tp201101617120180146432019139650.data.models.Locations
import pt.isec.amov.tp201101617120180146432019139650.data.models.PointOfInterest
import pt.isec.amov.tp201101617120180146432019139650.data.repos.PointOfInterestRepository
import pt.isec.ans.locationmaps.utils.location.LocationHandler

data class Coordinates(val team: String,val latitude : Double, val longitude: Double)

@Suppress("UNCHECKED_CAST")
class MapsViewModelFactory(private val locationHandler: LocationHandler, private val locationViewModel: LocationViewModel,private val pointOfInterestViewModel: PointOfInterestViewModel, private val pointOfInterestRepository: PointOfInterestRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapsViewModel(locationHandler,locationViewModel,pointOfInterestViewModel,pointOfInterestRepository) as T
    }
}

class MapsViewModel(
    private val locationHandler: LocationHandler,
    private val locationViewModel: LocationViewModel,
    private val pointOfInterestViewModel: PointOfInterestViewModel,
    private val pointOfInterestRepository: PointOfInterestRepository
) : ViewModel() {


    // Permissions
    var coarseLocationPermission = false
    var fineLocationPermission = false
    var backgroundLocationPermission = false
    val searchQuery = mutableStateOf("")

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations: StateFlow<List<Location>> = _locations

    private val _selectedLocation = mutableStateOf<Locations?>(null)
    val selectedLocation: MutableState<Locations?>
        get() = _selectedLocation

    // Adicione uma StateFlow para os pontos de interesse
    private val _pointsOfInterest = MutableStateFlow<List<PointOfInterest>>(emptyList())
    val pointsOfInterest: StateFlow<List<PointOfInterest>> = _pointsOfInterest

    // Adicione uma MutableState para o local selecionado
    // var selectedLocation: Location? by mutableStateOf(null)

    private val _currentLocation = MutableLiveData(Location(null))
    val currentLocation : LiveData<Location>
        get() = _currentLocation

    /*
    private val locationEnabled : Boolean
        get() = <LocationHandler>.locationEnabled

     */
    private val _searchQuery = MutableLiveData("")
    val searchQuery1: LiveData<String>
        get() = _searchQuery


    // Método para lidar com o clique em um ponto de interesse no mapa
    fun onPointOfInterestClicked(pointOfInterest: PointOfInterest) {
        // Faça o que for necessário ao clicar em um ponto de interesse
        // Pode ser a abertura de detalhes ou qualquer outra ação desejada
    }

    // Método para obter locais e pontos de interesse da ViewModel de locais e pontos de interesse
    fun getLocationsAndPointsOfInterest() {
        viewModelScope.launch {
            // _locations.value = locationViewModel.locations.value
            _pointsOfInterest.value = pointOfInterestViewModel.pointsOfInterest.value
        }
    }


    val POIs = listOf(
        Coordinates("Liverpool", 53.430819, -2.960828),
        Coordinates("Manchester City", 53.482989, -2.200292),
        Coordinates("Manchester United", 53.463056, -2.291389),
        Coordinates("Bayern Munich", 48.218775, 11.624753),
        Coordinates("Barcelona", 41.38087, 2.122802),
        Coordinates("Real Madrid", 40.45306, -3.68835),
        Coordinates("SC Lamego", 41.09046, -7.81616)
    )

    init {
        locationHandler.onLocation = {
            _currentLocation.value = it
        }
    }

    // Método para atualizar a consulta
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun startLocationUpdates() {
        if (fineLocationPermission && coarseLocationPermission) {
            locationHandler.startLocationUpdates()
        }
    }

    fun stopLocationUpdates() {
        locationHandler.stopLocationUpdates()
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    fun onLocationClicked(location: Locations?) {
        _selectedLocation.value = location

        // Adicione a chamada para obter os pontos de interesse da localização clicada
        location?.let { clickedLocation ->
            viewModelScope.launch {
                // Chame a função do repositório para obter os pontos de
                val locationId = clickedLocation.id
                val pointsOfInterestForLocation = pointOfInterestRepository.getPointsOfInterestOfLocation(clickedLocation.id)

                // Atualize o StateFlow com os pontos de interesse obtidos
                _pointsOfInterest.value = pointsOfInterestForLocation
            }
        }
    }
}




