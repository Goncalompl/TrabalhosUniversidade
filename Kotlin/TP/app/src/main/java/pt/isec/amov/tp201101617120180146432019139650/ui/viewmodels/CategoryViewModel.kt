package pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.isec.amov.tp201101617120180146432019139650.data.models.Category
import pt.isec.amov.tp201101617120180146432019139650.data.models.PointOfInterest
import pt.isec.amov.tp201101617120180146432019139650.data.repos.CategoryRepository
import pt.isec.amov.tp201101617120180146432019139650.data.repos.PointOfInterestRepository

@Suppress("UNCHECKED_CAST")
class AddCategoryViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository, pointOfInterestRepository) as T
    }
}
class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
) : ViewModel() {

    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val imagePath: MutableState<String?> = mutableStateOf(null)

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _categoryAddedStatus = mutableStateOf(false)
    val categoryAddedStatus: State<Boolean>
        get() = _categoryAddedStatus

    private val _error = mutableStateOf<String?>(null)
    val error : MutableState<String?>
        get() = _error

    var categoryDetails : Category? = null

    private val _pointsOfInterestOfCategory = MutableStateFlow<List<PointOfInterest>>(emptyList())
    val pointsOfInterestOfCategory: StateFlow<List<PointOfInterest>> = _pointsOfInterestOfCategory

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = categoryRepository.getAllCategories()
        }
    }

    fun getPointsOfInterestOfCategory() {
        categoryDetails?.let { category ->
            viewModelScope.launch{
                _pointsOfInterestOfCategory.value =
                    pointOfInterestRepository.getPointsOfInterestOfCategory(category.id)
            }    }
        }

    /*
    private var listenerRegistration: ListenerRegistration? = null

     teste de mostar uma categoria
    init {
        viewModelScope.launch {
            listenerRegistration?.remove()
            val db = Firebase.firestore
            listenerRegistration = db.collection(Category.COLLECTION_NAME).document("didi")
                .addSnapshotListener { docSS, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (docSS != null && docSS.exists()) {
                        _cat.value = Category(
                            id = docSS.getString(Category.ID) ?: "",
                            title = docSS.getString(Category.TITLE) ?: "",
                            description = docSS.getString(Category.DESCRIPTION) ?: "",
                            image = docSS.getString(Category.IMAGE)
                        )
                    }
                }
        }
    }

     */


    fun addCategory() {
        categoryRepository.saveCategory(
            Category(
            title = title.value,
            description = description.value,
            image = imagePath.value
        )
        ) { isSuccessfull, exception ->
            if (isSuccessfull) {
                getCategories()
                _error.value = null
            } else {
                _error.value = exception?.message
            }

        }
    }


}