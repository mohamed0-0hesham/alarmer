package com.hesham0_0.alarmer.di

import android.content.Context
import androidx.room.Room
import com.hesham0_0.alarmer.data.alarm.AndroidAlarmScheduler
import com.hesham0_0.alarmer.data.local.AlarmerDatabase
import com.hesham0_0.alarmer.data.local.dao.AlarmDao
import com.hesham0_0.alarmer.data.local.dao.HabitDao
import com.hesham0_0.alarmer.data.local.dao.MedicineDao
import com.hesham0_0.alarmer.data.local.dao.TaskDao
import com.hesham0_0.alarmer.data.repository.AlarmRepositoryImpl
import com.hesham0_0.alarmer.data.repository.HabitRepositoryImpl
import com.hesham0_0.alarmer.data.repository.MedicineRepositoryImpl
import com.hesham0_0.alarmer.data.repository.TaskRepositoryImpl
import com.hesham0_0.alarmer.domain.alarm.AlarmScheduler
import com.hesham0_0.alarmer.domain.repository.AlarmRepository
import com.hesham0_0.alarmer.domain.repository.HabitRepository
import com.hesham0_0.alarmer.domain.repository.MedicineRepository
import com.hesham0_0.alarmer.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAlarmerDatabase(@ApplicationContext context: Context): AlarmerDatabase {
        return Room.databaseBuilder(
            context,
            AlarmerDatabase::class.java,
            "alarmer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(db: AlarmerDatabase): AlarmDao = db.alarmDao

    @Provides
    @Singleton
    fun provideMedicineDao(db: AlarmerDatabase): MedicineDao = db.medicineDao

    @Provides
    @Singleton
    fun provideHabitDao(db: AlarmerDatabase): HabitDao = db.habitDao

    @Provides
    @Singleton
    fun provideTaskDao(db: AlarmerDatabase): TaskDao = db.taskDao

    @Provides
    @Singleton
    fun provideAlarmRepository(dao: AlarmDao): AlarmRepository = AlarmRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideMedicineRepository(dao: MedicineDao): MedicineRepository = MedicineRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideHabitRepository(dao: HabitDao): HabitRepository = HabitRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository = TaskRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AndroidAlarmScheduler(context)
    }
}
