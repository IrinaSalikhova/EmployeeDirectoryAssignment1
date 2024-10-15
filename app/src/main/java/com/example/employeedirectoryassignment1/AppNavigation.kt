package com.example.employeedirectoryassignment1

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(startDestination: String = "loginScreen") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("loginScreen") { LoginScreen(navController = navController) }
        composable("employeeListScreen") { EmployeeListJson(navController = navController) }
        composable("employeeDetailScreen") { EmployeeDetailScreen() }
    }
}
