package com.example.comicbookrental.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.profileComponents.ProfileCard
import com.example.comicbookrental.ui.components.profileComponents.ProfileMenuSection

@Composable
fun ProfileScreen(
    onLogOut: () -> Unit,
    onProfileDetailClick: () -> Unit,
    onCartClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(profile = state.userProfile)
        Spacer(modifier = Modifier.height(24.dp))
        ProfileMenuSection(
            onProfileDetailClick = onProfileDetailClick,
            onCartClick = onCartClick,
            onWishlistClick = onWishlistClick,
            onHistoryClick = onHistoryClick
        )
        Spacer(modifier = Modifier.height(32.dp))
        BrutalistButton(
            text = "LOG OUT",
            onClick = {
                viewModel.logOut()
                onLogOut()
            }
        )
    }
}