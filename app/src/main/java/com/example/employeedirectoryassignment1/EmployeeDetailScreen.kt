package com.example.employeedirectoryassignment1

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.employeedirectoryassignment1.ui.theme.dimens
import androidx.compose.ui.Modifier
import com.example.employeedirectoryassignment1.roomDB.Employee

@Composable
fun EmployeeDetailScreen() {
    val context = LocalContext.current
    val employee = remember { getEmployeeFromPreferences(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small3)
    ) {
        Text(text = "Full Name: ${employee.fullName}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Job Title: ${employee.jobTitle}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Department: ${employee.department}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Gender: ${employee.gender}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Ethnicity: ${employee.ethnicity}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Age: ${employee.age}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Hire Date: ${employee.hireDate}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Salary: ${employee.salary}", style = MaterialTheme.typography.labelMedium)
    }
}

fun getEmployeeFromPreferences(context: Context): Employee {
    val sharedPreferences = context.getSharedPreferences("employee_prefs", Context.MODE_PRIVATE)
    return Employee(
        fullName = sharedPreferences.getString("fullName", "") ?: "",
        jobTitle = sharedPreferences.getString("jobTitle", "") ?: "",
        department = sharedPreferences.getString("department", "") ?: "",
        gender = sharedPreferences.getString("gender", "") ?: "",
        ethnicity = sharedPreferences.getString("ethnicity", "") ?: "",
        age = sharedPreferences.getInt("age", 0),
        hireDate = sharedPreferences.getString("hireDate", "") ?: "",
        salary = sharedPreferences.getInt("salary", 0)
    )
}