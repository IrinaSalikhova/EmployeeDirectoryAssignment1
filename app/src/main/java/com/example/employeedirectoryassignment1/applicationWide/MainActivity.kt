package com.example.employeedirectoryassignment1.applicationWide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.employeedirectoryassignment1.ui.theme.EmployeeDirectoryAssignment1Theme
import com.example.employeedirectoryassignment1.weatherApp.WeatherWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeDirectoryAssignment1Theme {
                AppNavigation(context = this)
                // Schedule the periodic work
                scheduleWeatherUpdates()

            }
        }
    }
    private fun scheduleWeatherUpdates() {
        val workRequest: WorkRequest = PeriodicWorkRequestBuilder<WeatherWorker>(3, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}

