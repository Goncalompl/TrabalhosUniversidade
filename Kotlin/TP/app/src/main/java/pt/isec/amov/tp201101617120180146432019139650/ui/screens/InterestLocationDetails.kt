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
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel

@Composable
fun InterestLocationDetails(
    pointOfInterestViewModel: PointOfInterestViewModel,
    navController: NavHostController = rememberNavController(),
){
    val pointOfInterest by pointOfInterestViewModel.selectedPointOfInterest

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
            text = pointOfInterestViewModel.pointOfInterestDetails!!.title,
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
            painter = imagemLocal,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(shapes.medium)
        )

 */

        // Descrição do Local
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pointOfInterestViewModel.pointOfInterestDetails!!.description,
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.10.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pointOfInterestViewModel.pointOfInterestDetails!!.categoryTitle,
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.10.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pointOfInterestViewModel.pointOfInterestDetails!!.locationTitle,
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.10.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pointOfInterestViewModel.pointOfInterestDetails!!.latitude.toString(),
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.10.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pointOfInterestViewModel.pointOfInterestDetails!!.longitude.toString(),
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(id = R.color.title),
                letterSpacing = 0.10.sp
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = { navController.navigate("Adicionar_classificacao_screen") },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Atribuir Classificação" ,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.title),
                    letterSpacing = 0.10.sp
                )
            )
        }



    }
    }

