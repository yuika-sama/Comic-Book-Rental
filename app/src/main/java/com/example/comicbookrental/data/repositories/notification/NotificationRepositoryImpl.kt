package com.example.comicbookrental.data.repositories.notification

import com.example.comicbookrental.data.entities.AppNotification
import com.example.comicbookrental.domain.repository.NotificationRepository
import com.example.comicbookrental.services.StorageManager
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val storageManager: StorageManager
) : NotificationRepository
{

    override fun getAllNotifications(): List<AppNotification>
    {
        return storageManager.getAppNotifications().sortedByDescending { it.timestamp }
    }

    override fun addNotification(notification: AppNotification)
    {
        val currentList = storageManager.getAppNotifications().toMutableList()
        currentList.add(notification)
        storageManager.saveAppNotifications(currentList)
    }

    override fun markAsRead(notificationId: String)
    {
        val currentList = storageManager.getAppNotifications().map {
            if (it.id == notificationId) it.copy(isRead = true) else it
        }
        storageManager.saveAppNotifications(currentList)
    }

    override fun markAllAsRead()
    {
        val currentList = storageManager.getAppNotifications().map { it.copy(isRead = true) }
        storageManager.saveAppNotifications(currentList)
    }

    override fun getUnreadCount(): Int
    {
        return storageManager.getAppNotifications().count { !it.isRead }
    }
}
