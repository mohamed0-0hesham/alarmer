package com.hesham0_0.alarmer.domain.usecase.quiz

import com.hesham0_0.alarmer.domain.model.MathProblem
import com.hesham0_0.alarmer.domain.repository.MathQuizRepository
import javax.inject.Inject

class GetMathQuizUseCase @Inject constructor(
    private val repository: MathQuizRepository
) {
    suspend operator fun invoke(): List<MathProblem> = repository.getMathProblems()
}
