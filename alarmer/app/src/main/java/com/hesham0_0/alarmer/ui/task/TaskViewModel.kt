package com.hesham0_0.alarmer.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesham0_0.alarmer.domain.model.PersistentTask
import com.hesham0_0.alarmer.domain.usecase.task.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val tasks: StateFlow<List<PersistentTask>> = getTasksUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
