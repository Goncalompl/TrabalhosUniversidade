package pt.isec.amov.tp201101617120180146432019139650.ui.screens

/*
import androidx.activity.viewModels
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import pt.isec.amov.tp201101617120180146432019139650.AMovApp
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AddCategoryViewModel
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AddCategoryViewModelFactory

@Composable
fun AddCategory(addCategoryViewModel: AddCategoryViewModel){

    var location by remember { mutableStateOf("")}

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

        // Spacer
        Spacer(modifier = Modifier.height(70.dp))

        // Location TextField
        TextField(
            value = addCategoryViewModel.title.value,
            onValueChange = { addCategoryViewModel.title.value = it },
            label = { Text("Adicionar Categoria") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Add Button
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

        Spacer(modifier = Modifier.height(50.dp))

        // Add Button
        Button(
            onClick = { addCategoryViewModel.addCategory() },
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
        Text(text = addCategoryViewModel.cat.value.toString())

    }



}

 */