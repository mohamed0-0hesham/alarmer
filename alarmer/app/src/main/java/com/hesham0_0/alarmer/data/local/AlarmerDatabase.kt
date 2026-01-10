package com.hesham0_0.alarmer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hesham0_0.alarmer.data.local.dao.AlarmDao
import com.hesham0_0.alarmer.data.local.dao.HabitDao
import com.hesham0_0.alarmer.data.local.dao.MedicineDao
import com.hesham0_0.alarmer.data.local.dao.TaskDao
import com.hesham0_0.alarmer.data.local.entity.AlarmEntity
import com.hesham0_0.alarmer.data.local.entity.HabitEntity
import com.hesham0_0.alarmer.data.local.entity.MedicineEntity
import com.hesham0_0.alarmer.data.local.entity.TaskEntity

@Database(
    entities = [
        AlarmEntity::class,
        MedicineEntity::class,
        HabitEntity::class,
        TaskEntity::class
    ],
    version = 1
)
abstract class AlarmerDatabase : RoomDatabase() {
    abstract val alarmDao: AlarmDao
    abstract val medicineDao: MedicineDao
    abstract val habitDao: HabitDao
    abstract val taskDao: TaskDao
}
