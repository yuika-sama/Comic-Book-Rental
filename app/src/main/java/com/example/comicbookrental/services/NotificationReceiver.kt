package com.example.comicbookrental.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.comicbookrental.MainActivity
import com.example.comicbookrental.data.entities.AppNotification
import com.example.comicbookrental.data.entities.AppNotificationType
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver()
{

    @Inject
    lateinit var storageManager: StorageManager

    override fun onReceive(context: Context, intent: Intent)
    {
        if (!storageManager.getNotificationsEnabled()) return

        val notification = generateRandomNotification()

        val currentList = storageManager.getAppNotifications().toMutableList()
        currentList.add(0, notification) // Add to top
        storageManager.saveAppNotifications(currentList)

        showSystemNotification(context, notification)

        Toast.makeText(context, "New Notification: ${notification.title}", Toast.LENGTH_LONG).show()
    }

    private fun generateRandomNotification(): AppNotification
    {
        val types = AppNotificationType.entries.toTypedArray()
        val type = types.random()

        val (title, message) = when (type)
        {
            AppNotificationType.RENTAL_REMINDER -> Pair(
                "Tick Tock, Nerd!",
                "\"Solo Leveling\" ghosting you in 2 days. Read it before it's gone!"
            )

            AppNotificationType.NEW_RELEASE -> Pair(
                "Oda Dropped a Banger",
                "New \"One Piece\" chapter is out! Read now, dodge spoilers later."
            )

            AppNotificationType.PROMO -> Pair(
                "RIP Your Wallet",
                "50% off weekend rentals. Capitalism wins again. Treat yourself!"
            )

            AppNotificationType.RECOMMENDATION -> Pair(
                "We Stalked Your Tastes",
                "Algorithm says you're weird enough to love \"Jujutsu Kaisen\". Try it."
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
