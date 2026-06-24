package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.AppNotification

interface NotificationRepository
{
    fun getAllNotifications(): List<AppNotification>
    fun addNotification(notification: AppNotification)
    fun markAsRead(notificationId: String)
    fun markAllAsRead()
    fun getUnreadCount(): Int
}