package com.example.alarm.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.alarm.R

object NotificationHelper {

    private const val NOTIFICATION_CHANNEL_ID = "default"
    fun createNotification(context: Context): Notification? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context)
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Alarm!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
        } else null
    }

    private fun createChannel(context: Context) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "default", NotificationManager.IMPORTANCE_DEFAULT)
             getManager(context).createNotificationChannel(channel)
         }
    }

    fun getManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}