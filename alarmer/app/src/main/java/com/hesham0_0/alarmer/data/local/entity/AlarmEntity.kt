package com.hesham0_0.alarmer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey val id: String,
    val hour: Int,
    val minute: Int,
    val daysOfWeek: String, // Comma separated IDs
    val quizType: String,
    val isLowerVolumeOnPickUp: Boolean,
    val isActive: Boolean
)
