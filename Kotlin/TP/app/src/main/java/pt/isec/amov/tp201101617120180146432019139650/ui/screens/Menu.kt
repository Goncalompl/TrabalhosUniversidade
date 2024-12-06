package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.unit.TextUnit

import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont



@Composable
fun Menu(navController: NavHostController = rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title Container with Top Padding
        Column(
            modifier = Modifier
                .padding(top = 1.dp)
        ) {
            // Title
            Text(
                text = stringResource(id = R.string.WelcomeTitle),
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

        // Spacer
        Spacer(modifier = Modifier.height(30.dp))

        // Ver Locais Button
        MenuOption(stringResource(id = R.string.Locations), onClick = { navController.navigate("locais_screen") })

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Locais de Interesse Button with smaller text size
        MenuOption(stringResource(id = R.string.PointsofInterest), fontSize = 24.sp, onClick = { navController.navigate("locais_interesse_screen") })

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Locais de Interesse Button with smaller text size
        MenuOption(stringResource(id = R.string.Categories), fontSize = 24.sp, onClick = { navController.navigate("categorias_screen") })

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Mapa Button
        MenuOption("Mapa Locais", onClick = {navController.navigate("mapa_screen")})

        Spacer(modifier = Modifier.height(16.dp))
        MenuOption("Mapa POI", onClick = {navController.navigate("mapaPOI_screen")})

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Créditos Button
        MenuOption(stringResource(id = R.string.Credits), onClick = {navController.navigate("creditos_screen")})

    }
}

@Composable
fun MenuOption(option: String, fontSize: TextUnit = 28.sp, onClick: () -> Unit) {
    // Option Button
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.button)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(60.dp),
    ) {
        Text(
            text = option,
            style = TextStyle(
                fontSize = fontSize,
                fontFamily = ButtonFont,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.15.sp
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    Menu()
}
