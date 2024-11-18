package com.example.employeedirectoryassignment1.applicationWide

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.employeedirectoryassignment1.ITSupport.LoginScreenIT
import com.example.employeedirectoryassignment1.ITSupport.MapView
import com.example.employeedirectoryassignment1.ITSupport.TaskList
import com.example.employeedirectoryassignment1.employeeDirectory.EmployeeDetailScreen
import com.example.employeedirectoryassignment1.employeeDirectory.EmployeeList
import com.example.employeedirectoryassignment1.employeeDirectory.LoginScreen
import com.example.employeedirectoryassignment1.weatherApp.RequestWeatherScreen
import com.example.employeedirectoryassignment1.weatherApp.WeatherScreen

@Composable
fun AppNavigation(startDestination: String = "navigationScreen",context: Context) {
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

        composable("ITSupport") { LoginScreenIT(navController = navController, context = context) }
        composable("ITTaskList") { TaskList(navController = navController) }
        composable(
            "MapView/{taskId}",
            listOf(navArgument("taskId") {
                type = NavType.IntType
            })) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            if (taskId != null) {
                MapView(navController = navController, taskId)
            } }

    }
}
