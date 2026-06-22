package com.example.comicbookrental.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Brand colors that the Material 3 [androidx.compose.material3.ColorScheme] has no slot for.
 *
 * Access from any Composable via `MaterialTheme.extendedColors` (see Theme.kt).
 */
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

/**
 * Default is the light set so previews/tests that forget to wrap in [ComicBookRentalTheme]
 * still render sensible colors.
 */
val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }
