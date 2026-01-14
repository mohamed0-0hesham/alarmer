package com.hesham0_0.alarmer.di

import com.hesham0_0.alarmer.data.repository.MathQuizRepositoryImpl
import com.hesham0_0.alarmer.domain.repository.MathQuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuizModule {

    @Binds
    @Singleton
    abstract fun bindMathQuizRepository(
        impl: MathQuizRepositoryImpl
    ): MathQuizRepository
}
