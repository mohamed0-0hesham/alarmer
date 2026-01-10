package com.hesham0_0.alarmer.domain.alarm

import com.hesham0_0.alarmer.domain.model.SmartAlarm

interface AlarmScheduler {
    fun schedule(alarm: SmartAlarm)
    fun cancel(alarm: SmartAlarm)
}
