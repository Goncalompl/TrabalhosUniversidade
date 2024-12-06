package pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.tp201101617120180146432019139650.data.models.Category
import pt.isec.amov.tp201101617120180146432019139650.data.models.Locations
import pt.isec.amov.tp201101617120180146432019139650.data.models.PointOfInterest
import pt.isec.amov.tp201101617120180146432019139650.data.repos.CategoryRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.LocationRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.PointOfInterestRepository

@Suppress("UNCHECKED_CAST")
class PointOfInterestViewModelFactory(
    private val pointOfInterestRepository: PointOfInterestRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PointOfInterestViewModel(pointOfInterestRepository, categoryRepository, locationRepository) as T
    }
}
class PointOfInterestViewModel(
    private val pointOfInterestRepository: PointOfInterestRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _selectedPointOfInterest = mutableStateOf<PointOfInterest?>(null)
    val selectedPointOfInterest: State<PointOfInterest?> = _selectedPointOfInterest


    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val latitude = mutableDoubleStateOf(0.0)
    val longitude = mutableDoubleStateOf(0.0)
    val imagePath: MutableState<String?> = mutableStateOf(null)

    private val _pointsOfInterest = MutableStateFlow<List<PointOfInterest>>(emptyList())
    val pointsOfInterest: StateFlow<List<PointOfInterest>> = _pointsOfInterest

    private val _error = mutableStateOf<String?>(null)
    val error: MutableState<String?>
        get() = _error

    var pointOfInterestDetails: PointOfInterest? = null

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _selectedCategory = mutableStateOf("")
    val selectedCategory: MutableState<String>
        get() = _selectedCategory
    private val _selectedCategoryId = mutableStateOf("")
    val selectedCategoryId: MutableState<String>
        get() = _selectedCategoryId

    private val _locations = MutableStateFlow<List<Locations>>(emptyList())
    val locations: StateFlow<List<Locations>> = _locations

    private val _selectedLocation = mutableStateOf("")
    val selectedLocation: MutableState<String>
        get() = _selectedLocation
    private val _selectedLocationId = mutableStateOf("")
    val selectedLocationId: MutableState<String>
        get() = _selectedLocationId

    init {
        getPointsOfInterest()
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = categoryRepository.getAllCategories()
        }
    }

    fun getLocations() {
        viewModelScope.launch {
            _locations.value = locationRepository.getAllLocations()
        }
    }

    private fun getPointsOfInterest() {
        viewModelScope.launch {
            _pointsOfInterest.value = pointOfInterestRepository.getAllPointsOfInterest()
        }
    }

    fun addPointOfInterest() {
        if (_selectedCategory.value.isEmpty() || _selectedCategoryId.value.isEmpty()) {
            _error.value = "Tem de escolher uma categoria"
            return
        }
        if (_selectedLocation.value.isEmpty() || _selectedLocationId.value.isEmpty()) {
            _error.value = "Tem de escolher uma localizacao"
            return
        }
        _error.value = null
        pointOfInterestRepository.savePointOfInterest(
            PointOfInterest(
                title = title.value,
                description = description.value,
                latitude = latitude.value,
                longitude = longitude.value,
                categoryId = _selectedCategoryId.value,
                categoryTitle = _selectedCategory.value,
                locationId = selectedLocationId.value,
                locationTitle = selectedLocation.value,
            )
        ) { isSuccessful, exception ->
            if (isSuccessful) {
                getPointsOfInterest()
                _error.value = null
            } else {
                _error.value = exception?.message
            }

        }
    }

    fun setSelectedPointOfInterest(pointOfInterest: PointOfInterest) {
        _selectedPointOfInterest.value = pointOfInterest
        Log.d("PointOfInterestViewModel", "Point of interest selected: $pointOfInterest")
    }

}
