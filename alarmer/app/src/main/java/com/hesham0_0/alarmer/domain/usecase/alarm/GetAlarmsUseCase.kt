package com.hesham0_0.alarmer.domain.usecase.alarm

import com.hesham0_0.alarmer.domain.model.SmartAlarm
import com.hesham0_0.alarmer.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    operator fun invoke(): Flow<List<SmartAlarm>> = repository.getAlarms()
}
