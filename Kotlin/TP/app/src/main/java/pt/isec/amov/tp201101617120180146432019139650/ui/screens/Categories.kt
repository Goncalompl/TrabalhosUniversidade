package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.TitleFont
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.data.models.Category
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.CategoryViewModel

@Composable
fun Categories(
    navController: NavHostController = rememberNavController(),
    categoryViewModel: CategoryViewModel,
    //onSelected : (String) -> Unit
) {
    var filtro by remember { mutableStateOf("") }

    var ordenarPorOrdemAlfabetica by remember { mutableStateOf(false) }

    //categoryViewModel.getCategories()
    // Use o ViewModel para obter as categorias do Firebase
    val categories by categoryViewModel.categories.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .padding(top = 1.dp)
        ) {
            Text(
                text = stringResource(id = R.string.Categories),
                style = TextStyle(
                    fontSize = 37.sp,
                    color = colorResource(id = R.color.title),
                    fontFamily = TitleFont,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.10.sp
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Pesquisa
        TextField(
            value = filtro,
            onValueChange = { filtro = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(stringResource(id = R.string.SearchCategory)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para alternar entre ordem padrão e ordem alfabética
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { ordenarPorOrdemAlfabetica = !ordenarPorOrdemAlfabetica },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = if (ordenarPorOrdemAlfabetica) "Ordenar Padrão" else "Nome ↑",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.title),
                        letterSpacing = 0.10.sp
                    )
                )
            }
            Button(
                onClick = { navController.navigate("Adicionar_categoria_screen") },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.AddCategory),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.title),
                        letterSpacing = 0.10.sp
                    )
                )
            }
        }

        // Spacer
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            val categoriasFiltrados = categories.filter { it.title.contains(filtro, ignoreCase = true) }
            val teste = listOf<String>("a", "b")
            teste.sorted()
            val categoriasOrdenados =
                if (ordenarPorOrdemAlfabetica) categoriasFiltrados.sortedBy { it.title } else categoriasFiltrados
            //val categoriasOrdenados =0
            //    if (ordenarPorOrdemAlfabetica) categoriasFiltrados.sorted() else categoriasFiltrados

            items(categoriasOrdenados, key = {it.id}) { categoria ->
                CategoryItem(categoria, navController, categoryViewModel)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



@Composable
fun CategoryItem(categoria: Category, navController: NavController, categoryViewModel: CategoryViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable{
                categoryViewModel.categoryDetails = categoria
                categoryViewModel.getPointsOfInterestOfCategory()
                navController.navigate("categoria_detalhes_screen")
            }
        //backgroundColor = colorResource(id = R.color.button)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = categoria.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = ButtonFont,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.title),
                    letterSpacing = 0.15.sp
                ),
            )
        }
    }
}
