package com.example.comicbookrental.data.entities

import kotlinx.serialization.Serializable

@Serializable
enum class AppNotificationType
{
    RENTAL_REMINDER,
    NEW_RELEASE,
    PROMO,
    RECOMMENDATION
}

@Serializable
data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val type: AppNotificationType
)
