package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import pt.isec.amov.tp201101617120180146432019139650.ui.theme.TitleFont
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.CategoryViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel

@Composable
//fun CategoryDetails(nomeLocal: String, descricaoLocal: String, imagemLocal: Painter,navController: NavHostController = rememberNavController()) {
fun CategoryDetails(
    pointOfInterestViewModel: PointOfInterestViewModel,
    categoryViewModel: CategoryViewModel ,
    navController: NavHostController = rememberNavController()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Nome do Local
        Text(
            text = categoryViewModel.categoryDetails!!.title,
            style = TextStyle(
                fontSize = 30.sp,
                color = colorResource(id = R.color.title),
                fontFamily = TitleFont,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))


        /*
        // Foto do Local
        Image(
            painter = categoryViewModel.categoryDetails!!.image,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(shapes.medium)
        )

         */

        // Descrição do Local
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = categoryViewModel.categoryDetails!!.description ?: "",
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.10.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )


        var filtro by remember { mutableStateOf("") }
        var ordenarPorOrdemAlfabetica by remember { mutableStateOf(false) }

        val pointsOfInterest by categoryViewModel.pointsOfInterestOfCategory.collectAsState()

        // Campo de Pesquisa
        TextField(
            value = filtro,
            onValueChange = { filtro = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(stringResource(id = R.string.SearchPOI)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Locais de Interesse
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            val locaisInteresseFiltrados =
                pointsOfInterest.filter { it.title.contains(filtro, ignoreCase = true) }
            val locaisInteresseOrdenados =
                if (ordenarPorOrdemAlfabetica) locaisInteresseFiltrados.sortedBy { it.title } else locaisInteresseFiltrados

            items(locaisInteresseOrdenados) { localInteresse ->
                LocalInteresseItem(localInteresse, pointOfInterestViewModel, navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}
