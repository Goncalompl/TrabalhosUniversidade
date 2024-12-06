package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.MapsViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POIMaps(
    viewModel: MapsViewModel,
    pointOfInterestViewModel: PointOfInterestViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val pointsOfInterest by pointOfInterestViewModel.pointsOfInterest.collectAsState()


    var geoPoint by remember {
        mutableStateOf(
            GeoPoint(
                viewModel.currentLocation.value?.latitude ?: 0.0,
                viewModel.currentLocation.value?.longitude ?: 0.0
            )
        )
    }
    var autoEnable by remember { mutableStateOf(false) }

    if (autoEnable) {
        LaunchedEffect(key1 = viewModel.currentLocation.value) {
            geoPoint = GeoPoint(
                viewModel.currentLocation.value?.latitude ?: 0.0,
                viewModel.currentLocation.value?.longitude ?: 0.0
            )
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf<String?>(null) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Latitude: ${viewModel.currentLocation.value?.latitude ?: 0.0}")
            Switch(checked = autoEnable, onCheckedChange = { autoEnable = it })
            Text(text = "Longitude: ${viewModel.currentLocation.value?.longitude ?: 0.0}")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxSize(0.5f)
                .clipToBounds()
                .background(Color(255, 240, 128))
        ) {
            AndroidView(factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(18.0)
                    controller.setCenter(geoPoint)

                    // Adicione um marcador para a localização atual
                    overlays.add(
                        Marker(this).apply {
                            position = geoPoint
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = "Localização Atual"
                        }
                    )

                    // Adicione um marcador para cada ponto de interesse na ViewModel de pontos de interesse
                    for (pointOfInterest in pointsOfInterest) {
                        val marker = Marker(this).apply {
                            position = GeoPoint(pointOfInterest.latitude, pointOfInterest.longitude)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = pointOfInterest.title
                            tag = pointOfInterest.id
                        }

                        overlays.add(marker)
                    }
                }
            },
                update = {
                    it.controller.setCenter(geoPoint)
                },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Adicione o TextField para a barra de pesquisa
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                // Atualize a lista quando o valor da pesquisa mudar
                //pointOfInterestViewModel.updateSearchQuery(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Pesquisar por nome") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Adicione a lista de pontos de interesse
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(pointsOfInterest.filter { it.title.contains(searchQuery, ignoreCase = true) }) { pointOfInterest ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(128, 224, 255),
                        contentColor = Color(0, 0, 128)
                    ),
                    onClick = {

                        geoPoint = GeoPoint(pointOfInterest.latitude, pointOfInterest.longitude)

                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = pointOfInterest.title, fontSize = 20.sp)
                        Text(text = "${pointOfInterest.latitude} ${pointOfInterest.longitude}", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
