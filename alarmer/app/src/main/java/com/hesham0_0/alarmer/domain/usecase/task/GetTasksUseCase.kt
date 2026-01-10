package com.hesham0_0.alarmer.domain.usecase.task

import com.hesham0_0.alarmer.domain.model.PersistentTask
import com.hesham0_0.alarmer.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<PersistentTask>> = repository.getTasks()
}
