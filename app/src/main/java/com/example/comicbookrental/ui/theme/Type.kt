package com.example.comicbookrental.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.R

val AntonFont = FontFamily(
    Font(R.font.anton_regular, FontWeight.Normal)
)

val HankenGroteskFont = FontFamily(
    Font(R.font.hanken_grotesk_regular, FontWeight.Normal),
    Font(R.font.hanken_grotesk_medium, FontWeight.Medium),
    Font(R.font.hanken_grotesk_semibold, FontWeight.SemiBold),
    Font(R.font.hanken_grotesk_bold, FontWeight.Bold)
)

val ComicAppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = AntonFont,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    ),

    // Headline
    headlineLarge = TextStyle(
        fontFamily = AntonFont,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = AntonFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),

    // Title
    titleLarge = TextStyle(
        fontFamily = AntonFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontFamily = AntonFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),

    // Body
    bodyLarge = TextStyle(
        fontFamily = HankenGroteskFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = HankenGroteskFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = HankenGroteskFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),

    // Label
    labelLarge = TextStyle(
        fontFamily = HankenGroteskFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = TextStyle(
        fontFamily = HankenGroteskFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
)