package com.hesham0_0.alarmer.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hesham0_0.alarmer.domain.model.SmartAlarm
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AlarmsScreen(
    onNavigateToCreateAlarm: () -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val alarms by viewModel.alarms.collectAsState()
    val featuredAlarm = alarms.firstOrNull { it.isActive }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        // Header
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Alarmer",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Smart Reminders",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Featured Alarm
            featuredAlarm?.let {
                item {
                    FeaturedAlarmCard(it)
                }
            }

            // Alarms List
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
fun FeaturedAlarmCard(alarm: SmartAlarm) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "October 26, Saturday",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = alarm.time.format(DateTimeFormatter.ofPattern("hh:mm")),
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = alarm.time.format(DateTimeFormatter.ofPattern("a")),
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ring in 7 hours 15 minutes", // TODO: Calculate actual time
                color = Color(0xFFFF453A),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun AlarmItem(
    alarm: SmartAlarm,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = alarm.time.format(DateTimeFormatter.ofPattern("hh:mm")),
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = alarm.time.format(DateTimeFormatter.ofPattern("a")),
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = getDaysString(alarm.daysOfWeek),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Switch(
                        checked = alarm.isActive,
                        onCheckedChange = { onToggle() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFFFF453A),
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color(0xFF3A3A3C),
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFFF453A),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Location: Home, 🧠 Quiz Dismissal: Active",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

private fun getDaysString(days: List<Int>): String {
    if (days.isEmpty()) return "Once"
    if (days.size == 7) return "Everyday"
    val dayChars = listOf("M", "T", "W", "T", "F", "S", "S")
    return days.sorted().joinToString("") { dayChars[it - 1] }
}
