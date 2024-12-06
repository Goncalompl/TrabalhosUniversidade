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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R
import pt.isec.amov.tp201101617120180146432019139650.ui.theme.ButtonFont
import pt.isec.amov.tp201101617120180146432019139650.ui.viewmodels.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel,
    login: Boolean
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error by remember { authenticationViewModel.error }
    val user by remember { authenticationViewModel.userEmail }

    LaunchedEffect(key1 = user ) {
        if (user != null && error == null) {
            navController.navigate("Menu")
        }
    }

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

        Spacer(modifier = Modifier.height(20.dp))

        if(error != null) {
            Text(text = "Error: $error", Modifier.background(Color(255, 0, 0)))
            Spacer(Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(100.dp))

        if (login) {
            Button(
                onClick = {
                          authenticationViewModel.signInWithEmail(
                              email = email.value,
                              password = password.value
                          )
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
        } else {
            Button(
                onClick = {
                    authenticationViewModel.createUserWithEmail(
                        email = email.value,
                        password = password.value
                    )
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
}

@Preview
@Composable
fun LoginPreview() {
    //Login()
}