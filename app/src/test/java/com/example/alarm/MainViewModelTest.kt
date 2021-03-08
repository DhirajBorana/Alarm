package com.example.alarm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.alarm.data.db.AlarmDao
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
class ViewModelTest {

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    lateinit var alarmRepo: AlarmDao
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        alarmRepo = FakeAlarmRepository()
        ServiceLocator.alarmRepository = alarmRepo
        viewModel = MainViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun add() = runBlockingTest {
        val alarm = Alarm(1, "5:20 PM", 12345, true)
        viewModel.add(alarm)
        val alarms = viewModel.alarms.getOrAwaitValue()
        Assert.assertEquals(alarmRepo.getAll().value, alarms)
    }

    @Test
    fun update() = runBlockingTest {
        val alarm = Alarm(1, "5:20 PM", 12345, false)
        viewModel.update(alarm)
        val alarms = viewModel.alarms.getOrAwaitValue()
        Assert.assertEquals(alarmRepo.getAll().value, alarms)
    }

    @Test
    fun delete() = runBlockingTest {
        val alarm = Alarm(1, "5:20 PM", 12345, false)
        viewModel.delete(alarm)
        val alarms = viewModel.alarms.getOrAwaitValue()
        Assert.assertEquals(alarmRepo.getAll().value, alarms)
    }
}
