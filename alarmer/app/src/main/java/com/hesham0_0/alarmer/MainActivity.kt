package com.hesham0_0.alarmer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hesham0_0.alarmer.ui.BottomNavItem
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

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem(Screen.Dashboard, Icons.Default.Home, "Home"),
        BottomNavItem(Screen.Alarms, Icons.Default.Notifications, "Alarms"),
        BottomNavItem(Screen.Medicine, Icons.Default.DateRange, "Meds"),
        BottomNavItem(Screen.Tasks, Icons.Default.List, "Tasks")
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            
            // Only show bottom bar on main screens
            if (items.any { it.screen.route == currentDestination?.route }) {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Dashboard.route,
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

@Composable
fun DashboardScreen() {
    Text(text = "Welcome to Alarmer", modifier = Modifier.padding(16.dp))
}
