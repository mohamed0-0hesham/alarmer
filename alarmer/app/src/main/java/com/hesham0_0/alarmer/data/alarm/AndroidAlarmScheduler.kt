package com.hesham0_0.alarmer.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hesham0_0.alarmer.data.receiver.AlarmReceiver
import com.hesham0_0.alarmer.domain.alarm.AlarmScheduler
import com.hesham0_0.alarmer.domain.model.SmartAlarm
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: SmartAlarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_ID", alarm.id)
        }
        
        // Find next trigger time based on daysOfWeek
        val nextTriggerTime = calculateNextTriggerTime(alarm)
        
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextTriggerTime,
            PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarm: SmartAlarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
    
    private fun calculateNextTriggerTime(alarm: SmartAlarm): Long {
        var triggerDateTime = LocalDateTime.now()
            .withHour(alarm.time.hour)
            .withMinute(alarm.time.minute)
            .withSecond(0)
            .withNano(0)

        if (triggerDateTime.isBefore(LocalDateTime.now())) {
            triggerDateTime = triggerDateTime.plusDays(1)
        }

        // Simplified for now: if daysOfWeek is empty, it's just a one-time alarm for next occurrence
        // If daysOfWeek is not empty, would need logic to find next matching day
        
        return triggerDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
