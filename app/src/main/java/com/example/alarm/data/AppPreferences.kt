package com.example.alarm.data

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "com.example.musicplayer"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val ALARM_ID = Pair("ALARM_ID", 0)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var alarmId: Int
        get() = preferences.getInt(ALARM_ID.first, ALARM_ID.second)
        set(value) = preferences.edit {
            it.putInt(ALARM_ID.first, value)
        }
}