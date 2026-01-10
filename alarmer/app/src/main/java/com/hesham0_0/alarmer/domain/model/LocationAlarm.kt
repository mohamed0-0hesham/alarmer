package com.hesham0_0.alarmer.domain.model

data class LocationAlarm(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radiusInMeters: Float,
    val isActive: Boolean = true
)
