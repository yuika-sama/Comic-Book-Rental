package com.example.comicbookrental.ui.screens.notifications

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel()
{

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init
    {
        loadNotifications()
    }

    fun loadNotifications()
    {
        val notifications = repository.getAllNotifications()
        val unreadCount = repository.getUnreadCount()
        _uiState.update { it.copy(notifications = notifications, unreadCount = unreadCount) }
    }

    fun markAsRead(notificationId: String)
    {
        repository.markAsRead(notificationId)
        loadNotifications()
    }

    fun markAllAsRead()
    {
        repository.markAllAsRead()
        loadNotifications()
    }
}
