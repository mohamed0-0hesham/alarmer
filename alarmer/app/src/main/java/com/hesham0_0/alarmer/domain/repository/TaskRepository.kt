package com.hesham0_0.alarmer.domain.repository

import com.hesham0_0.alarmer.domain.model.PersistentTask
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<PersistentTask>>
    suspend fun insertTask(task: PersistentTask)
    suspend fun deleteTask(task: PersistentTask)
    suspend fun updateTask(task: PersistentTask)
}
