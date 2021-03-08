package com.example.alarm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey val id: Int,
    @ColumnInfo val timeInString: String,
    @ColumnInfo val timeInMillis: Long,
    @ColumnInfo val enabled: Boolean
)