package com.example.alarm.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.ServiceLocator
import com.example.alarm.data.db.AlarmDao
import com.example.alarm.data.db.AlarmRepository
import com.example.alarm.data.db.AlarmDatabase
import com.example.alarm.data.model.Alarm
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val alarmRepo = ServiceLocator.provideAlarmRepository(application)

    val alarms = alarmRepo.getAll()

    fun add(alarm: Alarm) = viewModelScope.launch {
        alarmRepo.add(alarm)
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        alarmRepo.update(alarm)
    }

    fun delete(alarm: Alarm) = viewModelScope.launch {
        alarmRepo.delete(alarm)
    }
}