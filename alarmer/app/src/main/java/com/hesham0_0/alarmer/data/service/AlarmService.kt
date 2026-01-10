package com.hesham0_0.alarmer.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.hesham0_0.alarmer.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmId = intent?.getStringExtra("ALARM_ID")
        
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "ALARM_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this exists or use a default
            .setContentTitle("Alarm Ringing")
            .setContentText("Tap to solve the quiz and stop the alarm.")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(null, true) // Should point to QuizActivity
            .build()

        startForeground(1, notification)
        
        // TODO: Start playing ringtone and handle "lower volume on pick up"
        
        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "ALARM_CHANNEL",
            "Alarms",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(null, null)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
