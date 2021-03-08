package com.example.alarm.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.alarm.data.reciever.AlarmReceiver
import com.example.alarm.R
import com.example.alarm.data.AppPreferences
import com.example.alarm.data.model.Alarm
import com.example.alarm.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AlarmAdapter
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        initAdapter()
        viewModel.alarms.observe(this) { adapter.submitList(it) }
        binding.addFab.setOnClickListener { showTimePicker() }
    }

    private fun initAdapter() {
        adapter = AlarmAdapter(object : AlarmAdapter.Callback {
            override fun onTimeClicked(position: Int) {
                showTimePicker(adapter.currentList[position])
            }

            override fun onToggleSwitchClicked(position: Int) {
                val alarm = adapter.currentList[position]
                viewModel.update(alarm.copy(enabled = !alarm.enabled))
                if (!alarm.enabled) {
                    setAlarm(alarm = alarm)
                } else cancelAlarm(alarm)
            }

            override fun onMoreOptionsClicked(position: Int, view: View) {
                showMoreOptionMenu(adapter.currentList[position], view)
            }
        })
        binding.alarmRv.adapter = adapter
    }

    private fun showMoreOptionMenu(alarm: Alarm, view: View) {
        PopupMenu(this, view).apply {
            inflate(R.menu.delete)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        viewModel.delete(alarm)
                        cancelAlarm(alarm)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    private fun showTimePicker(alarm: Alarm? = null) {
        val timePicker = TimePickerFragment { _, hourOfDay, minute ->
            var newAlarm = alarm
            val calender = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            if (alarm == null) {
                newAlarm = Alarm(
                    id = AppPreferences.alarmId,
                    timeInString = DateFormat.getTimeInstance(DateFormat.SHORT)
                        .format(calender.time),
                    timeInMillis = calender.timeInMillis,
                    enabled = true
                )
                AppPreferences.alarmId = AppPreferences.alarmId.plus(1)
            } else {
                newAlarm = alarm.copy(
                    timeInString = DateFormat.getTimeInstance(DateFormat.SHORT)
                        .format(calender.time),
                    timeInMillis = calender.timeInMillis,
                )
            }
            viewModel.add(newAlarm)
            setAlarm(calender, newAlarm)
        }
        timePicker.show(supportFragmentManager, "time_picker")
    }

    private fun setAlarm(calender: Calendar? = null, alarm: Alarm) {
        val pendingIntent =
            PendingIntent.getBroadcast(this, alarm.id, Intent(this, AlarmReceiver::class.java), 0)
        val timeInMillis = if (calender != null) {
            if (calender.before(Calendar.getInstance())) {
                calender.add(Calendar.DATE, 1)
            }
            calender.timeInMillis
        } else {
            alarm.timeInMillis
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    private fun cancelAlarm(alarm: Alarm) {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, alarm.id, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}