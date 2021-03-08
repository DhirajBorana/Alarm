package com.example.alarm

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.alarm.data.db.AlarmDao
import com.example.alarm.data.db.AlarmDatabase
import com.example.alarm.data.db.AlarmRepository

object ServiceLocator {

    @Volatile
    var alarmRepository: AlarmDao? = null
    @VisibleForTesting set

    fun provideAlarmRepository(context: Context): AlarmDao {
        synchronized(this) {
            return alarmRepository ?: createAlarmRepository(context)
        }
    }

    private fun createAlarmRepository(context: Context): AlarmDao {
        val alarmDao = AlarmDatabase.getDatabase(context).alarmDao()
        val instance = AlarmRepository(alarmDao)
        alarmRepository = instance
        return instance
    }
}