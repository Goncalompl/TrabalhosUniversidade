package pt.isec.amov.tp201101617120180146432019139650.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.TitleFont
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.PointOfInterestViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddInterestLocation(
    pointOfInterestViewModel: PointOfInterestViewModel,
    navController: NavHostController = rememberNavController(),
){

    val error by remember { pointOfInterestViewModel.error }
    var dropDownExpandedCategory by remember { mutableStateOf(false) }
    var dropDownExpandedLocation by remember { mutableStateOf(false) }
    var dropDownTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val dropDownIconCategory = if (dropDownExpandedCategory)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    val dropDownIconLocation = if (dropDownExpandedLocation)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

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
                text = stringResource(id = R.string.AddInterestLocation),
                style = TextStyle(
                    fontSize = 37.sp,
                    color = colorResource(id = R.color.title),
                    fontFamily = TitleFont,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.10.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(error != null) {
            Text(text = "Error: $error", Modifier.background(Color(255, 0, 0)))
            Spacer(Modifier.height(16.dp))
        }

        TextField(
            value = pointOfInterestViewModel.title.value,
            onValueChange = { pointOfInterestViewModel.title.value = it },
            label = { Text(stringResource(id = R.string.AddInterestLocation)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = pointOfInterestViewModel.description.value,
            onValueChange = { pointOfInterestViewModel.description.value = it },
            label = { Text(stringResource(id = R.string.Description)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = false,
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Drop Down para escolher categoria
        OutlinedTextField(
            value = pointOfInterestViewModel.selectedCategory.value,
            onValueChange = {
                    pointOfInterestViewModel.selectedCategory.value = it
                            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    dropDownTextFieldSize = coordinates.size.toSize()
                },
            label = { Text(stringResource(id = R.string.CategoryName)) },
            trailingIcon = {
                Icon(dropDownIconCategory,"Categoria",
                    Modifier.clickable { dropDownExpandedCategory = !dropDownExpandedCategory })
            }
        )

        DropdownMenu(
            expanded = dropDownExpandedCategory,
            onDismissRequest = { dropDownExpandedCategory = false },
            modifier = Modifier
                .width( with(LocalDensity.current) { dropDownTextFieldSize.width.toDp() } )
                .padding(4.dp)
        ) {
            pointOfInterestViewModel.categories.value.forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category.title) },
                    onClick = {
                        pointOfInterestViewModel.selectedCategory.value = category.title
                        pointOfInterestViewModel.selectedCategoryId.value = category.id
                        dropDownExpandedCategory = false
                    })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Drop Down para escolher location
        OutlinedTextField(
            value = pointOfInterestViewModel.selectedLocation.value,
            onValueChange = {
                    pointOfInterestViewModel.selectedLocation.value = it
                            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    dropDownTextFieldSize = coordinates.size.toSize()
                },
            label = { Text(stringResource(id = R.string.Locations)) },
            trailingIcon = {
                Icon(dropDownIconLocation,"Location",
                    Modifier.clickable { dropDownExpandedLocation = !dropDownExpandedLocation })
            }
        )

        DropdownMenu(
            expanded = dropDownExpandedLocation,
            onDismissRequest = { dropDownExpandedLocation = false },
            modifier = Modifier
                .width( with(LocalDensity.current) { dropDownTextFieldSize.width.toDp() } )
                .padding(4.dp)
        ) {
            pointOfInterestViewModel.locations.value.forEach { location ->
                DropdownMenuItem(
                    text = { Text(text = location.title) },
                    onClick = {
                        pointOfInterestViewModel.selectedLocation.value = location.title
                        pointOfInterestViewModel.selectedLocationId.value = location.id
                        dropDownExpandedLocation = false
                    })
            }
        }

        TextField(
            value = pointOfInterestViewModel.latitude.doubleValue.toString(),
            onValueChange = { pointOfInterestViewModel.latitude.doubleValue = it.toDouble() },
            label = { Text(stringResource(id = R.string.latitude)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = pointOfInterestViewModel.longitude.doubleValue.toString(),
            onValueChange = {
                pointOfInterestViewModel.longitude.doubleValue = it.toDouble()
            },
            label = { Text(stringResource(id = R.string.longitude)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        /*
        TextField(
            value = category,
            onValueChange = { location = it },
            label = { Text("Categoria") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(50.dp))

         */

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

        Button(
            onClick = {
                pointOfInterestViewModel.addPointOfInterest()
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
