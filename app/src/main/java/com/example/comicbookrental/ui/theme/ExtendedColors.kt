package com.example.comicbookrental.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class ExtendedColors(
    val ink: Color,
    val success: Color,
    val warning: Color,
    val info: Color,
    val rating: Color,
)

val LightExtendedColors = ExtendedColors(
    ink = InkBlack,
    success = Success,
    warning = Warning,
    info = Info,
    rating = Rating,
)


val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }
