package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun firstScreen(navController: NavHostController = rememberNavController()) {

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

        // Logo (Replace with your logo)
        val imagem: Painter = painterResource(id = R.drawable.welcomeimage) // Substitua pelo nome real da sua imagem

        Image(
            painter = imagem,
            contentDescription = null,
            modifier = Modifier
                .size(220.dp)
                .clip(MaterialTheme.shapes.medium) // Ou substitua por qualquer outra forma de corte desejada
        )

        // Spacer
        Spacer(modifier = Modifier.height(50.dp))

        // Locais de Interesse Button with smaller text size
        MenuOption(stringResource(id = R.string.Login), fontSize = 24.sp, onClick = { navController.navigate("login_screen") })

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Mapa Button
        MenuOption(stringResource(id = R.string.Register), onClick = {navController.navigate("register_screen")})

    }
}

@Preview
@Composable
fun firstScreenPreview() {
    firstScreen()
}