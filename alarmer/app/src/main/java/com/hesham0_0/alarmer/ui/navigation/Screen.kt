package com.hesham0_0.alarmer.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Alarms : Screen("alarms")
    object Medicine : Screen("medicine")
    object Tasks : Screen("tasks")
    object Habits : Screen("habits")
    object LocationAlarms : Screen("location_alarms")
    object Settings : Screen("settings")
}
