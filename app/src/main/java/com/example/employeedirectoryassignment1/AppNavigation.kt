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
        composable("employeeListScreen") { EmployeeList(navController = navController) }
        composable("employeeDetailScreen/{selectedEmployeeName}") { navBackStackEntry ->
            val selectedEmployeeName =
                navBackStackEntry.arguments?.getString("selectedEmployeeName")
            EmployeeDetailScreen(selectedEmployeeName)
        }
    }
}
