package com.hesham0_0.alarmer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val reminderTime: String,
    val isCompletedToday: Boolean,
    val streak: Int
)
