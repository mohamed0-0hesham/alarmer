package com.hesham0_0.alarmer.data.repository

import com.hesham0_0.alarmer.data.local.dao.MedicineDao
import com.hesham0_0.alarmer.data.local.entity.MedicineEntity
import com.hesham0_0.alarmer.domain.model.MedicineReminder
import com.hesham0_0.alarmer.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val dao: MedicineDao
) : MedicineRepository {

    override fun getMedicineReminders(): Flow<List<MedicineReminder>> {
        return dao.getMedicineReminders().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertMedicineReminder(reminder: MedicineReminder) {
        dao.insertMedicineReminder(reminder.toEntity())
    }

    override suspend fun deleteMedicineReminder(reminder: MedicineReminder) {
        dao.deleteMedicineReminder(reminder.toEntity())
    }

    override suspend fun updateMedicineReminder(reminder: MedicineReminder) {
        dao.updateMedicineReminder(reminder.toEntity())
    }

    private fun MedicineEntity.toDomain(): MedicineReminder {
        return MedicineReminder(
            id = id,
            name = name,
            dosage = dosage,
            startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate), ZoneId.systemDefault()),
            endDate = endDate?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()) },
            intervalHours = intervalHours,
            timesPerDay = timesPerDay,
            isActive = isActive
        )
    }

    private fun MedicineReminder.toEntity(): MedicineEntity {
        return MedicineEntity(
            id = id,
            name = name,
            dosage = dosage,
            startDate = startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            endDate = endDate?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli(),
            intervalHours = intervalHours,
            timesPerDay = timesPerDay,
            isActive = isActive
        )
    }
}
