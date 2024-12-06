package pt.isec.amov.tp201101617120180146432019139650
/*
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isec.amov.tp201101617120180146432019139650.data.models.Category
import pt.isec.amov.tp201101617120180146432019139650.data.repos.CategoryRepository
import pt.isec.amov.tp201101617120180146432019139650.ui.screens.AddCategory
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.MyApplicationTheme
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AddCategoryViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AddCategoryViewModelFactory
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val app by lazy { application as AMovApp }
    private val vm : AddCategoryViewModel by viewModels{
        AddCategoryViewModelFactory(app.categoryRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", vm)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, vm: AddCategoryViewModel, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    AddCategory(addCategoryViewModel = vm)
    
    Text(text = vm.cats.value.toString())


}

 */