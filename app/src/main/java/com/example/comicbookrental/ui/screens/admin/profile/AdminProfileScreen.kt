package com.example.comicbookrental.ui.screens.admin.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.ui.components.adminComponents.AdminProfileCard
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.screens.profile.ProfileViewModel
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun AdminProfileScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.Spacing.ScreenPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdminProfileCard(profile = state.user)
        Spacer(modifier = Modifier.height(Dimens.Spacing.StackLg))
        BrutalistButton(
            text = "LOG OUT",
            onClick = {
                viewModel.logOut()
                onLogout()
            }
        )
    }
}
