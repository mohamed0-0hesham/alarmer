package com.hesham0_0.alarmer.domain.repository

import com.hesham0_0.alarmer.domain.model.MathProblem

interface MathQuizRepository {
    suspend fun getMathProblems(): List<MathProblem>
}
