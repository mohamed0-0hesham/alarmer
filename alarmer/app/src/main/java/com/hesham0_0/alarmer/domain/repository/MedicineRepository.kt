package com.hesham0_0.alarmer.domain.repository

import com.hesham0_0.alarmer.domain.model.MedicineReminder
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {
    fun getMedicineReminders(): Flow<List<MedicineReminder>>
    suspend fun insertMedicineReminder(reminder: MedicineReminder)
    suspend fun deleteMedicineReminder(reminder: MedicineReminder)
    suspend fun updateMedicineReminder(reminder: MedicineReminder)
}
