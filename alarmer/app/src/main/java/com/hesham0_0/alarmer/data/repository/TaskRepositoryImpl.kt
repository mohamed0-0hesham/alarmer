package com.hesham0_0.alarmer.data.repository

import com.hesham0_0.alarmer.data.local.dao.TaskDao
import com.hesham0_0.alarmer.data.local.entity.TaskEntity
import com.hesham0_0.alarmer.domain.model.PersistentTask
import com.hesham0_0.alarmer.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : TaskRepository {

    override fun getTasks(): Flow<List<PersistentTask>> {
        return dao.getTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertTask(task: PersistentTask) {
        dao.insertTask(task.toEntity())
    }

    override suspend fun deleteTask(task: PersistentTask) {
        dao.deleteTask(task.toEntity())
    }

    override suspend fun updateTask(task: PersistentTask) {
        dao.updateTask(task.toEntity())
    }

    private fun TaskEntity.toDomain(): PersistentTask {
        return PersistentTask(
            id = id,
            title = title,
            description = description,
            isDone = isDone,
            isSticky = isSticky
        )
    }

    private fun PersistentTask.toEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            title = title,
            description = description,
            isDone = isDone,
            isSticky = isSticky
        )
    }
}
