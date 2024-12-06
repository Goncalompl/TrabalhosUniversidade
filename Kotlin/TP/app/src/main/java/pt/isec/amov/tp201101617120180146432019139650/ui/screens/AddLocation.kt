package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.LocationViewModel

@Composable
fun AddLocation(
    locationViewModel: LocationViewModel,
    navController: NavHostController = rememberNavController()
){
    val error by remember { locationViewModel.error }

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
            // Title
            Text(
                text = stringResource(id = R.string.AddLocation),
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

        Spacer(modifier = Modifier.height(30.dp))

        if(error != null) {
            Text(text = "Error: $error", Modifier.background(Color(255, 0, 0)))
            Spacer(Modifier.height(16.dp))
        }

        TextField(
            value = locationViewModel.title.value,
            onValueChange = { locationViewModel.title.value = it },
            label = { Text(stringResource(id = R.string.LocationTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = locationViewModel.description.value,
            onValueChange = { locationViewModel.description.value = it },
            label = { Text(stringResource(id = R.string.LocationDescription)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = locationViewModel.latitude.value.toString(),
            onValueChange = { locationViewModel.latitude.value = it.toDouble() },
            label = { Text(stringResource(id = R.string.latitude)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = locationViewModel.longitude.value.toString(),
            onValueChange = {
                    locationViewModel.longitude.value = it.toDouble()
                            },
            label = { Text(stringResource(id = R.string.longitude)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle login click */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button)
            ),
            modifier = Modifier
                .padding(4.dp)
                .height(60.dp),
        ) {
            Text(
                text = stringResource(id = R.string.Image),
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

        // Add Button
        Button(
            onClick = {
                locationViewModel.addLocation()
                navController.navigateUp()
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(60.dp),
        ) {
            Text(
                text = stringResource(id = R.string.Add),
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
