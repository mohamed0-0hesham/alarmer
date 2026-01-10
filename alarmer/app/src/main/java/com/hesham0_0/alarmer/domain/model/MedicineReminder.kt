package com.hesham0_0.alarmer.domain.model

import java.time.LocalDateTime

data class MedicineReminder(
    val id: String,
    val name: String,
    val dosage: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val intervalHours: Int, // e.g., every 8 hours
    val timesPerDay: Int,
    val isActive: Boolean = true
)
