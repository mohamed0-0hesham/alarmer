package com.hesham0_0.alarmer.domain.model

data class Habit(
    val id: String,
    val title: String,
    val description: String,
    val reminderTime: String, // Or LocalTime
    val isCompletedToday: Boolean = false,
    val streak: Int = 0
)
