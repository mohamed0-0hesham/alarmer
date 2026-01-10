package com.hesham0_0.alarmer.domain.model

import java.time.LocalTime

data class SmartAlarm(
    val id: String,
    val time: LocalTime,
    val daysOfWeek: List<Int>, // 1-7 for Mon-Sun
    val quizType: QuizType,
    val isLowerVolumeOnPickUp: Boolean = true,
    val isActive: Boolean = true
)

sealed class QuizType {
    object Math : QuizType()
    object Shapes : QuizType()
    object Words : QuizType()
    object None : QuizType()
}
