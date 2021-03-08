package com.example.alarm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.alarm.data.db.AlarmDao
import com.example.alarm.data.db.AlarmRepository
import com.example.alarm.data.model.Alarm
import com.example.alarm.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AlarmRepositoryTest {

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    lateinit var alarmRepo: AlarmDao
    @Mock
    lateinit var alarmDao: AlarmDao

    val alarms = mutableListOf(
        Alarm(0, "5:20 PM", 1234, true),
        Alarm(1, "4:12 PM", 234534, true),
        Alarm(2, "12:00 AM", 5464, false),
        Alarm(3, "6:06 PM", 12456434, true),
        )
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        alarmRepo = AlarmRepository(alarmDao)
    }

    @Test
    fun getAll(){
        val alarm = Alarm(5, "5:54 PM", 12345, true)
        Mockito.`when`(alarmDao.getAll()).thenReturn(MutableLiveData(alarms))
        alarmRepo.getAll()
        Mockito.verify(alarmDao).getAll()
    }

    @Test
    fun add() = runBlockingTest {
        val alarm = Alarm(5, "5:54 PM", 12345, true)
        alarmRepo.add(alarm)
        Mockito.verify(alarmDao).add(alarm)
    }

    @Test
    fun update() = runBlockingTest {
        val alarm = Alarm(5, "5:54 PM", 12345, true)
        alarmRepo.update(alarm)
        Mockito.verify(alarmDao).update(alarm)
    }

    @Test
    fun delete() = runBlockingTest {
        val alarm = Alarm(5, "5:54 PM", 12345, true)
        alarmRepo.delete(alarm)
        Mockito.verify(alarmDao).delete(alarm)
    }
}
