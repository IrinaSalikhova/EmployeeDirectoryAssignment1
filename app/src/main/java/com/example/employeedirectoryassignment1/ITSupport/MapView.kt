package com.example.employeedirectoryassignment1.ITSupport

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.ITRoomDB.TaskDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapView(navController: NavHostController) {

    val context = LocalContext.current
    val database = TaskDatabase.getDatabase(context)

    val taskFlow = database.taskDao().getAllTasks()

    // Use LaunchedEffect to collect data and log it
    LaunchedEffect(Unit) {
        taskFlow.collect { tasks ->
            tasks.forEach { task ->
                Log.d("MapView", "Task: ${task.clientFirstName} ${task.clientLastName}, Address: ${task.address}")
            }
        }
    }


}