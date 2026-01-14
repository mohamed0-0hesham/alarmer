package com.hesham0_0.alarmer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hesham0_0.alarmer.ui.alarm.AlarmsScreen
import com.hesham0_0.alarmer.ui.alarm.CreateAlarmScreen
import com.hesham0_0.alarmer.ui.medicine.MedicineScreen
import com.hesham0_0.alarmer.ui.navigation.Screen
import com.hesham0_0.alarmer.ui.task.TasksScreen
import com.hesham0_0.alarmer.ui.theme.AlarmerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmerTheme {
                MainScreen()
            }
        }
    }
}

data class BottomNavItem(val screen: Screen, val icon: ImageVector, val label: String)

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem(Screen.Dashboard, Icons.Default.Home, "Home"),
        BottomNavItem(Screen.Alarms, Icons.Default.Notifications, "Alarms"),
        BottomNavItem(Screen.Medicine, Icons.Default.DateRange, "Meds"),
        BottomNavItem(Screen.Tasks, Icons.Default.List, "Tasks")
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                if (items.any { it.screen.route == currentDestination?.route }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        // Custom Bottom Bar Background
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(RoundedCornerShape(40.dp))
                                .background(Color(0xFF1C1C1E)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            items.forEach { item ->
                                val isSelected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
                                val color = if (isSelected) Color(0xFFFF453A) else Color.Gray
                                
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(top = 10.dp)
                                        .clickable {
                                            navController.navigate(item.screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(item.icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
                                    Text(item.label, color = color, fontSize = 10.sp)
                                }
                            }
                        }

                        // Floating Action Button in the center
                        Box(
                            modifier = Modifier
                                .offset(y = (-40).dp)
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF453A))
                                .clickable {
                                    navController.navigate(Screen.CreateAlarm.route)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(32.dp))
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Screen.Alarms.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Dashboard.route) { DashboardScreen() }
                composable(Screen.Alarms.route) { 
                    AlarmsScreen(onNavigateToCreateAlarm = { navController.navigate(Screen.CreateAlarm.route) }) 
                }
                composable(Screen.CreateAlarm.route) { 
                    CreateAlarmScreen(onPopBackStack = { navController.popBackStack() }) 
                }
                composable(Screen.Medicine.route) { MedicineScreen() }
                composable(Screen.Tasks.route) { TasksScreen() }
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    Text(text = "World Clock", color = Color.White, modifier = Modifier.padding(16.dp))
}
