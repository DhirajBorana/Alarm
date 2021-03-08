package com.example.alarm

import android.app.Application
import com.example.alarm.data.AppPreferences

class AlarmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}