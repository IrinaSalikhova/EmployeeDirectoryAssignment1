package com.example.employeedirectoryassignment1.ITSupport

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.ITRoomDB.Task
import com.example.employeedirectoryassignment1.ITRoomDB.TaskDatabase
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.buttonContainer
import com.example.employeedirectoryassignment1.ui.theme.buttonText
import com.example.employeedirectoryassignment1.ui.theme.dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(navController: NavHostController) {
    val context = LocalContext.current
    val database = TaskDatabase.getDatabase(context)
    val taskDao = database.taskDao()

    // State to hold the list of tasks
    var tasks by remember { mutableStateOf(emptyList<Task>()) }
    var userName by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state

    // Collect tasks from the database
    LaunchedEffect(Unit) {
        database.taskDao().getAllTasks().collect { taskList ->
            // Sort tasks by address
            tasks = taskList.sortedBy { it.address }
            taskList.forEach { task ->
                Log.d("TaskList", "Task: ${task.clientFirstName} ${task.clientLastName}, Address: ${task.address}")
            }
        }
        // Load user name from SharedPreferences
        val sharedPreferences = context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("username", "User  Name") ?: "User  Name"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = userName) }, // Display actual user name
                actions = {
                    Button(onClick = {
                        clearUser (context)  // Call the function to clear preferences
                        navController.navigate("ITSupport")
                    }) {
                        Text("Logout")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Add SnackbarHost
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onTaskClick = {
                                // Handle task modification (toggle completion status)
                                val updatedTask = task.copy(isDone = !task.isDone)
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        taskDao.updateTask(updatedTask)
                                        // Show success message
                                        snackbarHostState.showSnackbar("Task updated successfully")
                                    } catch (e: Exception) {
                                        // Show error message
                                        snackbarHostState.showSnackbar("Error updating task: ${e.message}")
                                    }
                                }
                            },
                            onAddressClick = {
                                navController.navigate("MapView/${task.id}")
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(MaterialTheme.dimens.buttonHeight),
                    onClick = {
                        navController.navigate("MapView")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.buttonContainer,
                        contentColor = MaterialTheme.colorScheme.buttonText
                    ),
                    shape = RoundedCornerShape(size = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.go_to_map),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    )
}

@Composable
fun TaskItem(task: Task, onTaskClick: () -> Unit, onAddressClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onTaskClick),
        verticalAlignment = Alignment.CenterVertically // Align items vertically
    ) {
        // Checkbox for task completion status
        Checkbox(
            checked = task.isDone,
            onCheckedChange = {
                onTaskClick() // Call the onTaskClick to update the task
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = "${task.clientFirstName} ${task.clientLastName}", fontWeight = FontWeight.Bold)
            Text(
                text = task.address,
                modifier = Modifier.clickable(onClick = onAddressClick),
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "Longitude: ${task.lon}")
            Text(text = "Latitude: ${task.lat}")
            Text(text = if (task.isDone) "Status: Completed" else "Status: Pending")
        }
    }
}

fun clearUser (context: Context) {
    val sharedPreferences = context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
}