package com.hesham0_0.alarmer.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hesham0_0.alarmer.data.service.AlarmService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getStringExtra("ALARM_ID") ?: return
        
        // Starting the AlarmService as a foreground service.
        // This is the correct way to trigger high-priority UI (fullScreenIntent) 
        // when the app is in the background or killed.
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("ALARM_ID", alarmId)
        }
        context.startForegroundService(serviceIntent)
    }
}
