package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

data class TopBarState(
    val isShowHeart: Boolean = false,
    val isInterested: Boolean = false,
    val onInterestedClick: () -> Unit = {}
)

val LocalTopBarState = compositionLocalOf { mutableStateOf(TopBarState()) }
