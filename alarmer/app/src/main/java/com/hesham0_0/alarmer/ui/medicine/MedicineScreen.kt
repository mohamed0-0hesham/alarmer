package com.hesham0_0.alarmer.ui.medicine

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
import com.hesham0_0.alarmer.domain.model.MedicineReminder

@Composable
fun MedicineScreen(
    viewModel: MedicineViewModel = hiltViewModel()
) {
    val reminders by viewModel.medicineReminders.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Open Add Medicine Dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Medicine")
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
            items(reminders) { reminder ->
                MedicineItem(reminder)
            }
        }
    }
}

@Composable
fun MedicineItem(reminder: MedicineReminder) {
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
                    text = reminder.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${reminder.dosage} • Every ${reminder.intervalHours}h",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Switch(checked = reminder.isActive, onCheckedChange = {})
        }
    }
}
