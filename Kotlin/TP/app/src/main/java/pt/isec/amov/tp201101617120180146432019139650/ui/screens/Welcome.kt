package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.painter.Painter
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont


@Composable
fun Welcome(
    title: String,
    navController: NavHostController?,
    vararg options: String
) {

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

                modifier = Modifier.padding(bottom = 100.dp)
            )

        }
        Column(
            modifier = Modifier
                .padding(top = 1.dp)
        ) {Spacer(modifier = Modifier.height(30.dp))}


        // Logo (Replace with your logo)
        val imagem: Painter = painterResource(id = R.drawable.welcomeimage) // Substitua pelo nome real da sua imagem

        Image(
            painter = imagem,
            contentDescription = null,
            modifier = Modifier
                .size(220.dp)
                .clip(shapes.medium) // Ou substitua por qualquer outra forma de corte desejada
        )


        Column(
            modifier = Modifier
                .padding(top = 1.dp)
        ) {Spacer(modifier = Modifier.height(30.dp))}

        Spacer(modifier = Modifier.height(30.dp))

        // Login Button
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .size(60.dp),

        ) {
            Text(
                text = stringResource(id = R.string.Login),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = ButtonFont,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.title),
                    letterSpacing = 0.15.sp
                ),

                )

        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .size(60.dp),

            ) {
            Text(
                text = stringResource(id = R.string.Register),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = ButtonFont,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.title),
                    letterSpacing = 0.15.sp
                ),
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePagePreview() {

        Welcome(
            "Sketches", null, "Solid", "Gallery", "Camera", "List"
        )

}