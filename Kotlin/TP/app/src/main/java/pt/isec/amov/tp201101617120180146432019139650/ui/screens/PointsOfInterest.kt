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
import androidx.compose.ui.tooling.preview.Preview
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
import pt.isec.amov.tp201101617120180146432019139650.data.models.PointOfInterest
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel

@Composable
fun PointsOfInterest(
    pointOfInterestViewModel: PointOfInterestViewModel,
    navController: NavHostController = rememberNavController(),
){

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
                text = stringResource(id = R.string.PointsofInterest),
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

        // Botão para alternar entre ordem padrão e ordem alfabética
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            Button(
                onClick = {
                    pointOfInterestViewModel.getCategories()
                    pointOfInterestViewModel.getLocations()
                    navController.navigate("adicionar_local_interesse_screen")
                          },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "Adicionar Local Interesse" ,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.title),
                        letterSpacing = 0.10.sp
                    )
                )
            }
        }

        ListarPontosDeInteresse(pointOfInterestViewModel = pointOfInterestViewModel, navController = navController)
    }
}

@Composable
fun ListarPontosDeInteresse(
    pointOfInterestViewModel: PointOfInterestViewModel,
    navController: NavHostController
) {

    var filtro by remember { mutableStateOf("") }
    var ordenarPorOrdemAlfabetica by remember { mutableStateOf(false) }

    val pointsOfInterest by pointOfInterestViewModel.pointsOfInterest.collectAsState()

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

@Composable
fun LocalInteresseItem(
    localInteresse: PointOfInterest,
    pointOfInterestViewModel: PointOfInterestViewModel,
    navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                pointOfInterestViewModel.pointOfInterestDetails = localInteresse
                navController.navigate("locais_interesse_detalhes_screen")
            }
        //backgroundColor = colorResource(id = R.color.button)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = localInteresse.title,
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
