package com.hesham0_0.alarmer.domain.repository

import com.hesham0_0.alarmer.domain.model.SmartAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAlarms(): Flow<List<SmartAlarm>>
    suspend fun insertAlarm(alarm: SmartAlarm)
    suspend fun deleteAlarm(alarm: SmartAlarm)
    suspend fun updateAlarm(alarm: SmartAlarm)
}
