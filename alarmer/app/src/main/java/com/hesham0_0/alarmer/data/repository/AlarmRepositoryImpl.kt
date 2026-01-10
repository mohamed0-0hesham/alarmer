package com.hesham0_0.alarmer.data.repository

import com.hesham0_0.alarmer.data.local.dao.AlarmDao
import com.hesham0_0.alarmer.data.local.entity.AlarmEntity
import com.hesham0_0.alarmer.domain.model.QuizType
import com.hesham0_0.alarmer.domain.model.SmartAlarm
import com.hesham0_0.alarmer.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val dao: AlarmDao
) : AlarmRepository {

    override fun getAlarms(): Flow<List<SmartAlarm>> {
        return dao.getAlarms().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertAlarm(alarm: SmartAlarm) {
        dao.insertAlarm(alarm.toEntity())
    }

    override suspend fun deleteAlarm(alarm: SmartAlarm) {
        dao.deleteAlarm(alarm.toEntity())
    }

    override suspend fun updateAlarm(alarm: SmartAlarm) {
        dao.updateAlarm(alarm.toEntity())
    }

    private fun AlarmEntity.toDomain(): SmartAlarm {
        return SmartAlarm(
            id = id,
            time = LocalTime.of(hour, minute),
            daysOfWeek = if (daysOfWeek.isEmpty()) emptyList() else daysOfWeek.split(",").map { it.toInt() },
            quizType = when (quizType) {
                "Math" -> QuizType.Math
                "Shapes" -> QuizType.Shapes
                "Words" -> QuizType.Words
                else -> QuizType.None
            },
            isLowerVolumeOnPickUp = isLowerVolumeOnPickUp,
            isActive = isActive
        )
    }

    private fun SmartAlarm.toEntity(): AlarmEntity {
        return AlarmEntity(
            id = id,
            hour = time.hour,
            minute = time.minute,
            daysOfWeek = daysOfWeek.joinToString(","),
            quizType = when (quizType) {
                is QuizType.Math -> "Math"
                is QuizType.Shapes -> "Shapes"
                is QuizType.Words -> "Words"
                else -> "None"
            },
            isLowerVolumeOnPickUp = isLowerVolumeOnPickUp,
            isActive = isActive
        )
    }
}
