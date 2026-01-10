package com.hesham0_0.alarmer.data.local.dao

import androidx.room.*
import com.hesham0_0.alarmer.data.local.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicine_reminders")
    fun getMedicineReminders(): Flow<List<MedicineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicineReminder(reminder: MedicineEntity)

    @Delete
    suspend fun deleteMedicineReminder(reminder: MedicineEntity)

    @Update
    suspend fun updateMedicineReminder(reminder: MedicineEntity)
}
