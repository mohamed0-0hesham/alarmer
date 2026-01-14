package com.hesham0_0.alarmer.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hesham0_0.alarmer.domain.alarm.AlarmScheduler
import com.hesham0_0.alarmer.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: AlarmRepository

    @Inject
    lateinit var scheduler: AlarmScheduler

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            
            scope.launch {
                val alarms = repository.getAlarms().first()
                alarms.filter { it.isActive }.forEach { alarm ->
                    scheduler.schedule(alarm)
                }
            }
        }
    }
}
