package com.example.comicbookrental.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.utils.StoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SettingsState(
    val appNotificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = false, // mocked
    val darkModeEnabled: Boolean = false, // mocked
    val saveDataMode: Boolean = false // mocked
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storeManager: StoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                appNotificationsEnabled = storeManager.getNotificationsEnabled()
            )
        }
    }

    fun toggleAppNotifications(enabled: Boolean) {
        storeManager.setNotificationsEnabled(enabled)
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
