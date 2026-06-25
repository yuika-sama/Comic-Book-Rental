package com.example.comicbookrental.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.services.StorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storageManager: StorageManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                appNotificationsEnabled = storageManager.getNotificationsEnabled()
            )
        }
    }

    fun toggleAppNotifications(enabled: Boolean) {
        storageManager.setNotificationsEnabled(enabled)
        _uiState.update { it.copy(appNotificationsEnabled = enabled) }
    }

    fun toggleEmailNotifications(enabled: Boolean) {
        // Mocking logic
        _uiState.update { it.copy(emailNotificationsEnabled = enabled) }
    }
    
    fun toggleDarkMode(enabled: Boolean) {
        // Mocking logic
        _uiState.update { it.copy(darkModeEnabled = enabled) }
    }

    fun toggleSaveDataMode(enabled: Boolean) {
        // Mocking logic
        _uiState.update { it.copy(saveDataMode = enabled) }
    }
}
