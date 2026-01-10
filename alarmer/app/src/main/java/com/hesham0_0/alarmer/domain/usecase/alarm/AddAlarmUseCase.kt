package com.hesham0_0.alarmer.domain.usecase.alarm

import com.hesham0_0.alarmer.domain.alarm.AlarmScheduler
import com.hesham0_0.alarmer.domain.model.SmartAlarm
import com.hesham0_0.alarmer.domain.repository.AlarmRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository,
    private val scheduler: AlarmScheduler
) {
    suspend operator fun invoke(alarm: SmartAlarm) {
        repository.insertAlarm(alarm)
        if (alarm.isActive) {
            scheduler.schedule(alarm)
        }
    }
}
