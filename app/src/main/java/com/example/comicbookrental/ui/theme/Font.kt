package com.example.comicbookrental.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.example.comicbookrental.R

val Anton = FontFamily(
    Font(R.font.anton_regular, FontWeight.Normal)
)

@OptIn(ExperimentalTextApi::class)
private fun hankenGrotesk(weight: FontWeight) = Font(
    resId = R.font.hanken_grotesk_variable,
    weight = weight,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight))
)

val HankenGrotesk = FontFamily(
    hankenGrotesk(FontWeight.Normal),   // 400
    hankenGrotesk(FontWeight.Medium),   // 500
    hankenGrotesk(FontWeight.Bold),     // 700
)