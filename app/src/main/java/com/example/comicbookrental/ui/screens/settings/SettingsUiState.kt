package com.example.comicbookrental.ui.screens.settings

data class SettingsUiState(
    val appNotificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = false,
    val darkModeEnabled: Boolean = false,
    val saveDataMode: Boolean = false
)