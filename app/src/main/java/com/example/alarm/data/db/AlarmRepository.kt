package com.example.alarm.data.db

import com.example.alarm.data.model.Alarm

class AlarmRepository(private val alarmDao: AlarmDao) : AlarmDao {

    override fun getAll() = alarmDao.getAll()

    override suspend fun add(alarm: Alarm) {
        alarmDao.add(alarm)
    }

    override suspend fun update(alarm: Alarm) {
        alarmDao.update(alarm)
    }

    override suspend fun delete(alarm: Alarm) {
        alarmDao.delete(alarm)
    }
}