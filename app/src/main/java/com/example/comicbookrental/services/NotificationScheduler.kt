package com.example.comicbookrental.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object NotificationScheduler
{

    fun scheduleRepeatingNotifications(context: Context)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the repeating alarm for every 5 minutes (300,000 milliseconds)
        // This schedule calculate from 0:00 every day, but for practical
        // 5-minute repeating, we can just start from the current time + 5 mins, 
        // or align it to the next 5-minute block of the current hour.

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            val currentMinute = get(Calendar.MINUTE)
            val next5MinuteBlock = ((currentMinute / 5) + 1) * 5
            set(Calendar.MINUTE, next5MinuteBlock)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val intervalMillis = 1 * 60 * 1000L

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            pendingIntent
        )
    }
}
