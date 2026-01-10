package com.hesham0_0.alarmer.domain.model

import java.time.LocalTime

data class PrayerTime(
    val name: String,
    val time: LocalTime,
    val isAutoAlarmEnabled: Boolean = true
)
