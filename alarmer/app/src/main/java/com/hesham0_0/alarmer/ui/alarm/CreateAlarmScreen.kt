package com.hesham0_0.alarmer.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hesham0_0.alarmer.domain.model.QuizType
import com.hesham0_0.alarmer.domain.model.SmartAlarm
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    onPopBackStack: () -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val initialTime = LocalTime.now()
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = false
    )
    
    var selectedQuizType by remember { mutableStateOf<QuizType>(QuizType.Math) }
    var selectedDays by remember { mutableStateOf(setOf<Int>()) }
    var isQuizTypeExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Alarm") },
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Alternative Save button in TopBar for convenience
                    TextButton(onClick = {
                        viewModel.addAlarm(
                            SmartAlarm(
                                id = UUID.randomUUID().toString(),
                                time = LocalTime.of(timePickerState.hour, timePickerState.minute),
                                daysOfWeek = selectedDays.toList(),
                                quizType = selectedQuizType,
                                isActive = true
                            )
                        )
                        onPopBackStack()
                    }) {
                        Text("Save", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Clock UI (12h with AM/PM)
            TimePicker(
                state = timePickerState,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            HorizontalDivider()

            // Quiz Type Dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Quiz Type", style = MaterialTheme.typography.labelLarge)
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { isQuizTypeExpanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedQuizType.javaClass.simpleName)
                    }
                    DropdownMenu(
                        expanded = isQuizTypeExpanded,
                        onDismissRequest = { isQuizTypeExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(QuizType.Math, QuizType.Shapes, QuizType.Words, QuizType.None).forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.javaClass.simpleName) },
                                onClick = {
                                    selectedQuizType = type
                                    isQuizTypeExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Days Selection
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Repeat on", style = MaterialTheme.typography.labelLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val days = listOf("M", "T", "W", "T", "F", "S", "S")
                    days.forEachIndexed { index, day ->
                        val dayNum = index + 1
                        val isSelected = selectedDays.contains(dayNum)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedDays = if (isSelected) {
                                    selectedDays - dayNum
                                } else {
                                    selectedDays + dayNum
                                }
                            },
                            label = { Text(day) }
                        )
                    }
                }
            }

            // Primary Save Button
            Button(
                onClick = {
                    viewModel.addAlarm(
                        SmartAlarm(
                            id = UUID.randomUUID().toString(),
                            time = LocalTime.of(timePickerState.hour, timePickerState.minute),
                            daysOfWeek = selectedDays.toList(),
                            quizType = selectedQuizType,
                            isActive = true
                        )
                    )
                    onPopBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Save Alarm", style = MaterialTheme.typography.titleMedium)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
