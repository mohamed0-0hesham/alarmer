package com.hesham0_0.alarmer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine_reminders")
data class MedicineEntity(
    @PrimaryKey val id: String,
    val name: String,
    val dosage: String,
    val startDate: Long, // Epoch millis
    val endDate: Long?,
    val intervalHours: Int,
    val timesPerDay: Int,
    val isActive: Boolean
)
