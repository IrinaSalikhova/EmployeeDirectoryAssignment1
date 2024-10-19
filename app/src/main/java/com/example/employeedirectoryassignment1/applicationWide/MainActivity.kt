package com.example.employeedirectoryassignment1.applicationWide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.employeedirectoryassignment1.ui.theme.EmployeeDirectoryAssignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeDirectoryAssignment1Theme {
               AppNavigation()

            }
        }
    }
}