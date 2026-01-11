package com.hesham0_0.alarmer.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesham0_0.alarmer.domain.alarm.AlarmScheduler
import com.hesham0_0.alarmer.domain.model.SmartAlarm
import com.hesham0_0.alarmer.domain.repository.AlarmRepository
import com.hesham0_0.alarmer.domain.usecase.alarm.AddAlarmUseCase
import com.hesham0_0.alarmer.domain.usecase.alarm.GetAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val repository: AlarmRepository,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    val alarms: StateFlow<List<SmartAlarm>> = getAlarmsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAlarm(alarm: SmartAlarm) {
        viewModelScope.launch {
            addAlarmUseCase(alarm)
        }
    }

    fun toggleAlarm(alarm: SmartAlarm) {
        viewModelScope.launch {
            val updatedAlarm = alarm.copy(isActive = !alarm.isActive)
            repository.updateAlarm(updatedAlarm)
            if (updatedAlarm.isActive) {
                scheduler.schedule(updatedAlarm)
            } else {
                scheduler.cancel(updatedAlarm)
            }
        }
    }
    
    fun deleteAlarm(alarm: SmartAlarm) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
            scheduler.cancel(alarm)
        }
    }
}
