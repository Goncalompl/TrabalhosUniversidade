package pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.tp201101617120180146432019139650.data.models.Locations
import pt.isec.amov.tp201101617120180146432019139650.data.models.PointOfInterest
import pt.isec.amov.tp201101617120180146432019139650.data.repos.LocationRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.PointOfInterestRepository

@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory(
    private val locationRepository: LocationRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
    ) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel(locationRepository, pointOfInterestRepository) as T
    }
}

class LocationViewModel(
    private val locationRepository: LocationRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,

    ) : ViewModel() {

    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val latitude = mutableDoubleStateOf(0.0)
    val longitude = mutableDoubleStateOf(0.0)
    val imagePath: MutableState<String?> = mutableStateOf(null)

    private val _locations = MutableStateFlow<List<Locations>>(emptyList())
    val locations: StateFlow<List<Locations>> = _locations

    private val _error = mutableStateOf<String?>(null)
    val error : MutableState<String?>
        get() = _error

    var locationsDetails : Locations? = null

    private val _pointsOfInterestOfLocation = MutableStateFlow<List<PointOfInterest>>(emptyList())
    val pointsOfInterestOfLocation: StateFlow<List<PointOfInterest>> = _pointsOfInterestOfLocation


    init {
        getLocations()
    }
    private fun getLocations() {
        viewModelScope.launch {
            _locations.value = locationRepository.getAllLocations()
        }
    }

    fun getPointsOfInterestOfLocation() {
        locationsDetails?.let { location ->
            viewModelScope.launch{
                _pointsOfInterestOfLocation.value =
                    pointOfInterestRepository.getPointsOfInterestOfLocation(location.id)
            }    }
    }


    /*
    private var listenerRegistratioin: ListenerRegistration? = null

    init {
        viewModelScope.launch {
            listenerRegistratioin?.remove()
            val db = Firebase.firestore
            listenerRegistratioin = db.collection(Location.COLLECTION_NAME).document("boas")
                .addSnapshotListener(){ docSS, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (docSS != null && docSS.exists()) {
                        _cat.value = Location(
                            id = docSS.getString(Location.ID) ?: "",
                            title = docSS.getString(Location.TITLE) ?: "",
                            description = docSS.getString(Location.DESCRIPTION) ?: "",
                            latitude = docSS.getDouble(Location.LATITUDE) ?: 0.0,
                            longitude = docSS.getDouble(Location.LONGITUDE) ?: 0.0
                            //coordinates = docSS.getString(Location.COORDINATES),
                            //state = docSS.getString(State)
                        )
                    }
                }
        }
    }

     */

    fun addLocation() {
        locationRepository.saveLocation(
            Locations(
                title = title.value,
                description = description.value,
                latitude = latitude.value,
                longitude = longitude.value
            )
        ) { isSuccessfull, exception ->
            if (isSuccessfull) {
                getLocations()
                _error.value = null
            } else {
                _error.value = exception?.message
            }

        }
    }

}