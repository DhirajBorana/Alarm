package com.example.alarm.data.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarm.utils.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notification = NotificationHelper.createNotification(it)
            NotificationHelper.getManager(it).notify(1, notification)
        }
    }
}