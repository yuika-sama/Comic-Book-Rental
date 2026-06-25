package com.example.comicbookrental.ui.screens.admin.manage_users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.adminComponents.AdminEmptyState
import com.example.comicbookrental.ui.components.adminComponents.AdminErrorState
import com.example.comicbookrental.ui.components.adminComponents.AdminLoadingState
import com.example.comicbookrental.ui.components.adminComponents.AdminUserCard
import com.example.comicbookrental.ui.components.adminComponents.UserActionDialog
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun ManageUsersScreen(
    modifier: Modifier = Modifier,
    viewModel: ManageUsersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val editor by viewModel.editor.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.Spacing.ScreenPadding)
    ) {
        BrutalistTextField(
            value = editor.searchQuery,
            onValueChange = viewModel::onSearchChange,
            label = "Search users",
            placeholder = "Email...",
            leadingIcon = Icons.Default.Search
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        when (uiState) {
            is ManageUsersUiState.Loading -> AdminLoadingState()

            is ManageUsersUiState.Error -> AdminErrorState(
                message = (uiState as ManageUsersUiState.Error).message
            )

            is ManageUsersUiState.Content -> {
                val users = (uiState as ManageUsersUiState.Content).users
                if (users.isEmpty()) {
                    AdminEmptyState(
                        message = if (editor.searchQuery.isNotBlank())
                            "No users match your search."
                        else
                            "No users yet."
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
                        contentPadding = PaddingValues(bottom = Dimens.Spacing.SectionSpacing)
                    ) {
                        items(users, key = { it.email }) { user ->
                            AdminUserCard(
                                user = user,
                                onBan = { viewModel.onBanClick(user) },
                                onUnban = { viewModel.onUnbanClick(user) }
                            )
                        }
                    }
                }
            }
        }
    }

    editor.pendingAction?.let { action ->
        UserActionDialog(
            email = action.user.email,
            ban = action.ban,
            onConfirm = viewModel::onActionConfirm,
            onDismiss = viewModel::onActionDismiss
        )
    }
}
