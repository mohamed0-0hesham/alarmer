package com.hesham0_0.alarmer.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hesham0_0.alarmer.ui.quiz.QuizActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getStringExtra("ALARM_ID") ?: return
        
        val quizIntent = Intent(context, QuizActivity::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        context.startActivity(quizIntent)
    }
}
