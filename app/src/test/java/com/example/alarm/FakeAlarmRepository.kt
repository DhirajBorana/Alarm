package com.example.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alarm.data.db.AlarmDao
import com.example.alarm.data.model.Alarm

class FakeAlarmRepository : AlarmDao {
    private val alarms = mutableListOf<Alarm>()
    override fun getAll(): LiveData<List<Alarm>> {
        return MutableLiveData(alarms)
    }

    override suspend fun add(alarm: Alarm) {
        alarms.add(alarm)
    }

    override suspend fun update(alarm: Alarm) {
        alarms.add(alarm)
    }

    override suspend fun delete(alarm: Alarm) {
        alarms.remove(alarm)
    }
}