package com.example.employeedirectoryassignment1.applicationWide

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.employeedirectoryassignment1.ITSupport.LoginScreenIT
import com.example.employeedirectoryassignment1.ITSupport.MapView
import com.example.employeedirectoryassignment1.ITSupport.TaskList
import com.example.employeedirectoryassignment1.employeeDirectory.EmployeeDetailScreen
import com.example.employeedirectoryassignment1.employeeDirectory.EmployeeList
import com.example.employeedirectoryassignment1.employeeDirectory.LoginScreen
import com.example.employeedirectoryassignment1.weatherApp.RequestWeatherScreen
import com.example.employeedirectoryassignment1.weatherApp.WeatherScreen

@Composable
fun AppNavigation(startDestination: String = "navigationScreen") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("navigationScreen") { AssignmentNavigationScreen(navController = navController) }
        composable("loginScreen") { LoginScreen(navController = navController) }
        composable("employeeListScreen") { EmployeeList(navController = navController) }
        composable("employeeDetailScreen/{selectedEmployeeName}") { navBackStackEntry ->
            val selectedEmployeeName =
                navBackStackEntry.arguments?.getString("selectedEmployeeName")
            EmployeeDetailScreen(selectedEmployeeName)
        }
        composable("requestWeatherScreen") { RequestWeatherScreen(navController = navController) }
        composable("weatherScreen") { WeatherScreen(navController = navController) }

        composable("ITSupport") { LoginScreenIT(navController = navController) }
        composable("ITTaskList") { TaskList(navController = navController) }
        composable("MapView") { MapView(navController = navController) }

    }
}
