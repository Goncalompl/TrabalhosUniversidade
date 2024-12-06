package pt.isec.amov.tp201101617120180146432019139650.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pt.isec.amov.tp201101617120180146432019139650.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


@OptIn(ExperimentalTextApi::class)
val TitleFont= FontFamily(
    Font(
        R.font.title,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(950),
            FontVariation.width(30f),
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val ButtonFont= FontFamily(
    Font(
        R.font.button1,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(950),
            FontVariation.width(30f),
        )
    )
)