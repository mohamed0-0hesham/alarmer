package com.hesham0_0.alarmer.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.hesham0_0.alarmer.R
import com.hesham0_0.alarmer.ui.quiz.QuizActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmId = intent?.getStringExtra("ALARM_ID")
        
        createNotificationChannel()
        
        val fullScreenIntent = Intent(this, QuizActivity::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_USER_ACTION)
        }
        
        val fullScreenPendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "ALARM_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarmer")
            .setContentText("Wake Up! Solve the quiz to stop the alarm.")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
            .setVibrate(longArrayOf(0, 500, 500, 500))
            .setOngoing(true)
            .setAutoCancel(false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        startForeground(1, notification)
        
        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "ALARM_CHANNEL",
            "Alarms",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "High priority alarms with quiz"
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 500, 500)
            setSound(
                Settings.System.DEFAULT_ALARM_ALERT_URI,
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
