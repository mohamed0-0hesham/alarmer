package com.hesham0_0.alarmer.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesham0_0.alarmer.domain.model.MathProblem
import com.hesham0_0.alarmer.domain.usecase.quiz.GetMathQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getMathQuizUseCase: GetMathQuizUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        loadQuiz()
    }

    private fun loadQuiz() {
        viewModelScope.launch {
            val problems = getMathQuizUseCase()
            _uiState.update { it.copy(problems = problems, currentProblemIndex = 0) }
        }
    }

    fun submitAnswer(answer: String) {
        val currentState = _uiState.value
        val currentProblem = currentState.problems.getOrNull(currentState.currentProblemIndex) ?: return

        if (currentProblem.answer == answer.trim()) {
            val nextIndex = currentState.currentProblemIndex + 1
            if (nextIndex >= currentState.problems.size) {
                _uiState.update { it.copy(isFinished = true, showError = false) }
            } else {
                _uiState.update { it.copy(currentProblemIndex = nextIndex, showError = false) }
            }
        } else {
            _uiState.update { it.copy(showError = true) }
        }
    }
}

data class QuizUiState(
    val problems: List<MathProblem> = emptyList(),
    val currentProblemIndex: Int = 0,
    val isFinished: Boolean = false,
    val showError: Boolean = false
)
