package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import pt.isec.amov.tp201101617120180146432019139650.AMovApp

import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.CategoryViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AuthenticationViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.LocationViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.MapsViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    pointOfInterestViewModel: PointOfInterestViewModel,
    authenticationViewModel: AuthenticationViewModel,
    mapsViewModel: MapsViewModel,
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current
    val app = context.applicationContext as AMovApp


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.NavTitle)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.title),
                    titleContentColor = Color.White
                ),
                actions = {
                    if (authenticationViewModel.userEmail.value != null) {
                        Column {
                            Text(text = "Welcome ${authenticationViewModel.userEmail.value}")
                            Button(onClick = {
                                authenticationViewModel.signOut()
                                navController.navigate("first_screen")
                            }) {
                                Text(text = "Logout")
                            }
                        }
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = "first_screen",
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = "categoria_detalhes_screen/{nomeCategoria}/{descricaoCategoria}",
                arguments = listOf(
                    navArgument("nomeCategoria") { type = NavType.StringType },
                    navArgument("descricaoCategoria") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                CategoryDetails(categoryViewModel = categoryViewModel, navController = navController, pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("first_screen") {
                firstScreen(navController)
            }
            composable("login_screen") {
                Login(navController = navController, authenticationViewModel = authenticationViewModel, login = true)
            }
            composable(
                route = "Maps/{pointOfInterestId}",
                arguments = listOf(
                    navArgument("pointOfInterestId") { type = NavType.StringType }
                )
            ) {
                // Chame a tela de mapa e passe o ID do ponto de interesse
                Maps(
                    viewModel = mapsViewModel,
                    locationViewModel = locationViewModel,
                    pointOfInterestViewModel = pointOfInterestViewModel,
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable("register_screen") {
                Login(navController = navController, authenticationViewModel = authenticationViewModel, login = false)
            }
            composable("Menu") {
                Menu(navController)
            }
            composable("locais_screen") {
                Locations(navController = navController, locationViewModel = locationViewModel)
            }
            composable("locais_detalhes_screen") {
                //val imagemLocal: Painter = painterResource(id = R.drawable.welcomeimage)
                LocationDetails(navController = navController, locationViewModel = locationViewModel, pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("locais_interesse_detalhes_screen") {
                //val imagemLocal: Painter = painterResource(id = R.drawable.welcomeimage)
                InterestLocationDetails(navController = navController, pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("locais_interesse_screen") {
                PointsOfInterest(navController = navController, pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("categorias_screen") {
                Categories(navController = navController, categoryViewModel = categoryViewModel)
            }
            composable("adicionar_categoria_screen") {
                AddCategory(navController = navController, categoryViewModel = categoryViewModel)
            }
            composable("adicionar_local_screen") {
                AddLocation(navController = navController, locationViewModel = locationViewModel)
            }
            composable("adicionar_local_interesse_screen") {
                AddInterestLocation(navController = navController, pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("adicionar_classificacao_screen") {
                AddClassification(navController)
            }
            composable("categoria_detalhes_screen") {
                CategoryDetails(categoryViewModel = categoryViewModel, navController = navController, pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("mapa_screen") {
                Maps(viewModel = mapsViewModel, locationViewModel = locationViewModel, navController = navController,pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("mapaPOI_screen") {
                POIMaps(viewModel = mapsViewModel, navController = navController,pointOfInterestViewModel = pointOfInterestViewModel)
            }
            composable("creditos_screen") {
                Credits(navController)
            }
        }
    }
}

