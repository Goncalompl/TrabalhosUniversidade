package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.CategoryViewModel

@Composable
fun AddCategory(
    categoryViewModel: CategoryViewModel,
    navController: NavHostController = rememberNavController()
){
    val error by remember { categoryViewModel.error }

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
                text = stringResource(id = R.string.AddCategory),
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

        Spacer(modifier = Modifier.height(70.dp))

        if(error != null) {
            Text(text = "Error: $error", Modifier.background(Color(255, 0, 0)))
            Spacer(Modifier.height(16.dp))
        }

        TextField(
            value = categoryViewModel.title.value,
            onValueChange = { categoryViewModel.title.value = it },
            label = { Text(stringResource(id = R.string.CategoryName)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Description TextField
        TextField(
            value = categoryViewModel.description.value,
            onValueChange = { categoryViewModel.description.value = it },
            label = { Text(stringResource(id = R.string.CategoryDescription)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Add Button
        Button(
            onClick = { /* Lógica para escolher uma imagem */ },
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

        Spacer(modifier = Modifier.height(50.dp))

        // Add Button
        Button(
            onClick = {
                categoryViewModel.addCategory()
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
