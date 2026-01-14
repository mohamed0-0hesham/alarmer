package com.hesham0_0.alarmer.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hesham0_0.alarmer.domain.model.MathProblem
import com.hesham0_0.alarmer.ui.theme.AlarmerTheme

@Composable
fun QuizScreen(
    onDismiss: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Disable back button
    BackHandler(enabled = true) { }

    if (uiState.isFinished) {
        LaunchedEffect(Unit) {
            onDismiss()
        }
    }

    QuizContent(
        uiState = uiState,
        onSubmitAnswer = { viewModel.submitAnswer(it) }
    )
}

@Composable
fun QuizContent(
    uiState: QuizUiState,
    onSubmitAnswer: (String) -> Unit
) {
    var userAnswer by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "Wake Up!",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Solve 10 problems to stop the alarm",
                color = Color.Gray,
                fontSize = 16.sp
            )

            LinearProgressIndicator(
                progress = { (uiState.currentProblemIndex + 1) / 10f },
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFF453A),
                trackColor = Color(0xFF1C1C1E)
            )

            val currentProblem = uiState.problems.getOrNull(uiState.currentProblemIndex)
            if (currentProblem != null) {
                Text(
                    text = "${currentProblem.problem} =",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFF453A),
                        unfocusedBorderColor = Color.Gray
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )

                if (uiState.showError) {
                    Text(
                        text = "Wrong answer! Try again.",
                        color = Color(0xFFFF453A),
                        fontSize = 14.sp
                    )
                }

                Button(
                    onClick = {
                        onSubmitAnswer(userAnswer)
                        userAnswer = ""
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF453A))
                ) {
                    Text("Submit", fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreenPreview() {
    AlarmerTheme {
        QuizContent(
            uiState = QuizUiState(
                problems = listOf(
                    MathProblem("22 + 5", "27"),
                    MathProblem("112 + 54", "166")
                ),
                currentProblemIndex = 0,
                showError = true
            ),
            onSubmitAnswer = {}
        )
    }
}
