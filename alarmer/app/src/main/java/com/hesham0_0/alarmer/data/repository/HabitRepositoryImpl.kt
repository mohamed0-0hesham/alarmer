package com.hesham0_0.alarmer.data.repository

import com.hesham0_0.alarmer.data.local.dao.HabitDao
import com.hesham0_0.alarmer.data.local.entity.HabitEntity
import com.hesham0_0.alarmer.domain.model.Habit
import com.hesham0_0.alarmer.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val dao: HabitDao
) : HabitRepository {

    override fun getHabits(): Flow<List<Habit>> {
        return dao.getHabits().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        dao.insertHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        dao.deleteHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        dao.updateHabit(habit.toEntity())
    }

    private fun HabitEntity.toDomain(): Habit {
        return Habit(
            id = id,
            title = title,
            description = description,
            reminderTime = reminderTime,
            isCompletedToday = isCompletedToday,
            streak = streak
        )
    }

    private fun Habit.toEntity(): HabitEntity {
        return HabitEntity(
            id = id,
            title = title,
            description = description,
            reminderTime = reminderTime,
            isCompletedToday = isCompletedToday,
            streak = streak
        )
    }
}
