package com.example.comicbookrental.ui.screens.admin.manage_users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.entities.AdminUser
import com.example.comicbookrental.domain.repository.AdminUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageUsersViewModel @Inject constructor(
    private val repository: AdminUserRepository
) : ViewModel()
{
    private val _editor = MutableStateFlow(ManageUsersEditorState())
    val editor: StateFlow<ManageUsersEditorState> = _editor.asStateFlow()

    val uiState: StateFlow<ManageUsersUiState> =
        combine(
            repository.getUsers(),
            _editor.map { it.searchQuery }.distinctUntilChanged()
        ) { users, query ->
            ManageUsersUiState.Content(applyFilter(users, query)) as ManageUsersUiState
        }
            .catch { e -> emit(ManageUsersUiState.Error(e.message ?: "Couldn't load users")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ManageUsersUiState.Loading,
            )

    init {
        viewModelScope.launch { repository.refresh() }
    }

    private fun applyFilter(list: List<AdminUser>, query: String): List<AdminUser> {
        val q = query.trim()
        if (q.isEmpty()) return list
        return list.filter { it.email.contains(q, ignoreCase = true) }
    }

    fun onSearchChange(query: String) {
        _editor.update { it.copy(searchQuery = query) }
    }

    fun onBanClick(user: AdminUser) {
        _editor.update { it.copy(pendingAction = UserAction(user, ban = true)) }
    }

    fun onUnbanClick(user: AdminUser) {
        _editor.update { it.copy(pendingAction = UserAction(user, ban = false)) }
    }

    fun onActionDismiss() {
        _editor.update { it.copy(pendingAction = null) }
    }

    fun onActionConfirm() {
        val action = _editor.value.pendingAction ?: return
        viewModelScope.launch {
            if (action.ban) repository.banUser(action.user.email)
            else repository.unbanUser(action.user.email)
            _editor.update { it.copy(pendingAction = null) }
        }
    }
}
