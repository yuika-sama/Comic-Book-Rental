package com.example.comicbookrental.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.comicbookrental.MainActivity
import com.example.comicbookrental.R
import com.example.comicbookrental.data.entities.AppNotification
import com.example.comicbookrental.data.entities.AppNotificationType
import com.example.comicbookrental.utils.StoreManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver()
{

    @Inject
    lateinit var storeManager: StoreManager

    override fun onReceive(context: Context, intent: Intent)
    {
        if (!storeManager.getNotificationsEnabled()) return

        val notification = generateRandomNotification()

        val currentList = storeManager.getAppNotifications().toMutableList()
        currentList.add(0, notification) // Add to top
        storeManager.saveAppNotifications(currentList)

        showSystemNotification(context, notification)
    }

    private fun generateRandomNotification(): AppNotification
    {
        val types = AppNotificationType.entries.toTypedArray()
        val type = types.random()

        val (title, message) = when (type)
        {
            AppNotificationType.RENTAL_REMINDER -> Pair(
                "Rental Expiring Soon",
                "Your rental for \"Solo Leveling\" expires in 2 days. Don't forget to read it!"
            )

            AppNotificationType.NEW_RELEASE -> Pair(
                "New Chapter Released",
                "A new chapter of \"One Piece\" is now available to read!"
            )

            AppNotificationType.PROMO -> Pair(
                "Weekend Sale!",
                "Get 50% off all seasonal rentals this weekend only."
            )

            AppNotificationType.RECOMMENDATION -> Pair(
                "Recommended for You",
                "Based on your reading history, we think you'll love \"Jujutsu Kaisen\"."
            )
        }

        return AppNotification(
            id = UUID.randomUUID().toString(),
            title = title,
            message = message,
            timestamp = System.currentTimeMillis(),
            isRead = false,
            type = type
        )
    }

    private fun showSystemNotification(context: Context, notification: AppNotification)
    {
        val channelId = "comic_rental_notifications"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                channelId,
                "App Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General notifications for Comic Book Rental"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(notification.id.hashCode(), builder.build())
    }
}
