package com.example.comicbookrental.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Raw color tokens from DESIGN.md ("Heroic Kinetic" design system).
 *
 * These mirror the Material 3 color roles 1:1 and are consumed by [LightColors] in Theme.kt.
 * Do NOT call these directly from UI — use `MaterialTheme.colorScheme.*` so the app stays
 * theme-able. For brand colors that have no Material slot, use [ExtendedColors]
 * via `MaterialTheme.extendedColors` instead.
 */

// --- Primary (Action Orange) ---
val Primary = Color(0xFFA93100)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFD43F00)
val OnPrimaryContainer = Color(0xFFFFFBFF)
val InversePrimary = Color(0xFFFFB59E)

// --- Secondary (Hero Blue) ---
val Secondary = Color(0xFF0040E0)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFF2E5BFF)
val OnSecondaryContainer = Color(0xFFEFEFFF)

// --- Tertiary ---
val Tertiary = Color(0xFF005DAA)
val OnTertiary = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFF0075D5)
val OnTertiaryContainer = Color(0xFFFEFCFF)

// --- Error ---
val Error = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)
val ErrorContainer = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF93000A)

// --- Background / Surface (Paper White) ---
val Background = Color(0xFFFCF9F8)
val OnBackground = Color(0xFF1C1B1B)
val Surface = Color(0xFFFCF9F8)
val OnSurface = Color(0xFF1C1B1B)
val SurfaceVariant = Color(0xFFE5E2E1)
val OnSurfaceVariant = Color(0xFF5C4037)
val SurfaceTint = Color(0xFFAD3200)

// --- Surface containers (M3 tonal layering for the "stacked issue" effect) ---
val SurfaceDim = Color(0xFFDCD9D9)
val SurfaceBright = Color(0xFFFCF9F8)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFF6F3F2)
val SurfaceContainer = Color(0xFFF0EDEC)
val SurfaceContainerHigh = Color(0xFFEBE7E7)
val SurfaceContainerHighest = Color(0xFFE5E2E1)

// --- Outline / Inverse ---
val Outline = Color(0xFF916F65)
val OutlineVariant = Color(0xFFE6BEB2)
val InverseSurface = Color(0xFF313030)
val InverseOnSurface = Color(0xFFF3F0EF)

// ---------------------------------------------------------------------------
// Brand extras — semantics with no Material 3 slot. Exposed via ExtendedColors.
// ---------------------------------------------------------------------------

/** Ink Black — used for the 2pt brutalist borders and hard-offset shadows. */
val InkBlack = Color(0xFF121212)

/** Status colors from DESIGN.md (Hero Green success, Yellow Alert warning, info). */
val Success = Color(0xFF16A34A)
val Warning = Color(0xFFEAB308)
val Info = Color(0xFF0284C7)

/** Rating star fill. */
val Rating = Color(0xFFFBBF24)

// Text
val TextPrimary = Color(0xFF121212)
val TextSecondary = Color(0xFF666666)
val TextDisabled = Color(0xFFAAAAAA)

// Border
val Border = Color(0xFFD4D4D4)

val FacebookColorPrimary = Color(0xFF1777F1)
