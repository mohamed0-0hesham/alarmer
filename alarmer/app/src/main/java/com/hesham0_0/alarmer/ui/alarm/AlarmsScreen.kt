package com.hesham0_0.alarmer.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hesham0_0.alarmer.domain.model.SmartAlarm

@Composable
fun AlarmsScreen(
    onNavigateToCreateAlarm: () -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val alarms by viewModel.alarms.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateAlarm) {
                Icon(Icons.Default.Add, contentDescription = "Add Alarm")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(alarms) { alarm ->
                AlarmItem(
                    alarm = alarm,
                    onToggle = { viewModel.toggleAlarm(alarm) }
                )
            }
        }
    }
}

@Composable
fun AlarmItem(
    alarm: SmartAlarm,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = String.format("%02d:%02d", alarm.time.hour, alarm.time.minute),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Quiz: ${alarm.quizType.javaClass.simpleName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Switch(
                checked = alarm.isActive,
                onCheckedChange = { onToggle() }
            )
        }
    }
}
