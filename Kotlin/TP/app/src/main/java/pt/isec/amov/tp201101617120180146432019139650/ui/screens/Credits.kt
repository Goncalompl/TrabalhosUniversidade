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
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.tp201101617120180146432019139650.R


@Composable
fun Credits(navController: NavHostController = rememberNavController()) {

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
                text = stringResource(id = R.string.Credits),
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

        CreditsItems("Alunos :", listOf("Inês Estêvão", "Gonçalo Pereira", "Gonçalo Leite"))

        CreditsItems("Disciplina :", listOf("Arquiteturas Móveis"))

        CreditsItems("Curso :", listOf("Engenharia Informática"))

        Text(
            text = "Ano letivo: 23/24",
            style = TextStyle(
                fontSize = 20.sp,
                color = colorResource(id = R.color.title),
                fontFamily = TitleFont,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.05.sp
            ),
            modifier = Modifier.padding(bottom = 15.dp)
        )

    }
}

@Composable
fun CreditsItems(category: String, names: List<String>) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        // Título da seção
        Text(
            text = category,
            style = TextStyle(
                fontSize = 20.sp,
                color = colorResource(id = R.color.title),
                fontFamily = TitleFont,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.05.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Itens de crédito
        names.forEach { name ->
            Items(name)
        }
    }
}

@Composable
fun Items(name: String) {
    Text(
        text = name,
        style = TextStyle(
            fontSize = 20.sp,
            color = colorResource(id = R.color.title),
            fontFamily = TitleFont,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.05.sp
        ),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Preview
@Composable
fun CreditsPreview() {
    Credits()
}