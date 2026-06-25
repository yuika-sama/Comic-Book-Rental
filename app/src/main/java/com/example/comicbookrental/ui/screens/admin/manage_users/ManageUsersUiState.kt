package com.example.comicbookrental.ui.screens.admin.manage_users

import com.example.comicbookrental.data.entities.AdminUser

sealed interface ManageUsersUiState {
    data object Loading : ManageUsersUiState

    data class Content(val users: List<AdminUser>) : ManageUsersUiState

    data class Error(val message: String) : ManageUsersUiState
}

data class ManageUsersEditorState(
    val searchQuery: String = "",
    val pendingAction: UserAction? = null,
)

data class UserAction(
    val user: AdminUser,
    val ban: Boolean,
)
