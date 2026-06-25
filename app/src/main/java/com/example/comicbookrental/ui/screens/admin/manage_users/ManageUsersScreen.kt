package com.example.comicbookrental.ui.screens.admin.manage_users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            is ManageUsersUiState.Loading -> LoadingState()

            is ManageUsersUiState.Error -> ErrorState(
                message = (uiState as ManageUsersUiState.Error).message
            )

            is ManageUsersUiState.Content -> {
                val users = (uiState as ManageUsersUiState.Content).users
                if (users.isEmpty()) {
                    EmptyState(isSearching = editor.searchQuery.isNotBlank())
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

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyState(isSearching: Boolean) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = if (isSearching) "No users match your search." else "No users yet.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
