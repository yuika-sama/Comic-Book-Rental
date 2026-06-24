package com.example.comicbookrental.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationTab<T: Any>(
    val label: String,
    val route: T,
    val icon: ImageVector
)
