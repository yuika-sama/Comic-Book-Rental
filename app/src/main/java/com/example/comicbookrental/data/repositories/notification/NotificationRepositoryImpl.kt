package com.example.comicbookrental.data.repositories.notification

import com.example.comicbookrental.data.entities.AppNotification
import com.example.comicbookrental.domain.repository.NotificationRepository
import com.example.comicbookrental.utils.StoreManager
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val storeManager: StoreManager
) : NotificationRepository
{

    override fun getAllNotifications(): List<AppNotification>
    {
        return storeManager.getAppNotifications().sortedByDescending { it.timestamp }
    }

    override fun addNotification(notification: AppNotification)
    {
        val currentList = storeManager.getAppNotifications().toMutableList()
        currentList.add(notification)
        storeManager.saveAppNotifications(currentList)
    }

    override fun markAsRead(notificationId: String)
    {
        val currentList = storeManager.getAppNotifications().map {
            if (it.id == notificationId) it.copy(isRead = true) else it
        }
        storeManager.saveAppNotifications(currentList)
    }

    override fun markAllAsRead()
    {
        val currentList = storeManager.getAppNotifications().map { it.copy(isRead = true) }
        storeManager.saveAppNotifications(currentList)
    }

    override fun getUnreadCount(): Int
    {
        return storeManager.getAppNotifications().count { !it.isRead }
    }
}
