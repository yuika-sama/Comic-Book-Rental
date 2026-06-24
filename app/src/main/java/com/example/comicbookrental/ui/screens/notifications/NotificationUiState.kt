package com.example.comicbookrental.ui.screens.notifications

import com.example.comicbookrental.data.entities.AppNotification

data class NotificationUiState(
    val notifications: List<AppNotification> = emptyList(),
    val unreadCount: Int = 0
)
