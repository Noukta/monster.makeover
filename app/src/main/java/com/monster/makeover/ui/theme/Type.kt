package com.monster.makeover.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.monster.makeover.R

val MainFont = FontFamily(
    Font(R.font.tiktok)
)
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = MainFont,
        fontSize = 34.sp
    ),
    displayMedium = TextStyle(
        fontFamily = MainFont,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MainFont,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MainFont,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MainFont,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MainFont,
        fontSize = 10.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MainFont,
        fontSize = 20.sp
    )
)