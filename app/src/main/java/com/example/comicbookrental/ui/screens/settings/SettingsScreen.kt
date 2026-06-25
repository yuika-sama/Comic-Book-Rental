package com.example.comicbookrental.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader
import com.example.comicbookrental.ui.components.commonComponents.halftoneBackground
import com.example.comicbookrental.ui.components.settingComponents.SettingsToggleItem
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .halftoneBackground()
            .padding(16.dp)
    ) {
        SectionHeader(
            title = "NOTIFICATIONS",
            modifier = Modifier.padding(bottom = 16.dp),
            titleColor = InkBlack
        )
        
        SettingsToggleItem(
            title = "App Notifications",
            subtitle = "Receive push alerts for new releases",
            checked = state.appNotificationsEnabled,
            onCheckedChange = { viewModel.toggleAppNotifications(it) }
        )
        SettingsToggleItem(
            title = "Email Notifications",
            subtitle = "Weekly digest and updates",
            checked = state.emailNotificationsEnabled,
            onCheckedChange = { viewModel.toggleEmailNotifications(it) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionHeader(
            title = "PREFERENCES (MOCK)",
            modifier = Modifier.padding(bottom = 16.dp),
            titleColor = InkBlack
        )

//        SettingsToggleItem(
//            title = "Dark Mode",
//            subtitle = "Use dark theme everywhere",
//            checked = state.darkModeEnabled,
//            onCheckedChange = { viewModel.toggleDarkMode(it) }
//        )
        SettingsToggleItem(
            title = "Data Saver",
            subtitle = "Load lower quality images",
            checked = state.saveDataMode,
            onCheckedChange = { viewModel.toggleSaveDataMode(it) }
        )
    }
}