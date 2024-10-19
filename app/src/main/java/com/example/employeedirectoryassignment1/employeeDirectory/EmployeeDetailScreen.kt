package com.example.employeedirectoryassignment1.employeeDirectory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.employeedirectoryassignment1.ui.theme.dimens
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.roomDB.Employee
import com.example.employeedirectoryassignment1.roomDB.EmployeeDatabase

@Composable
fun EmployeeDetailScreen(employeeName: String?) {
    val context = LocalContext.current
    val employeeDao = remember { EmployeeDatabase.getDatabase(context).employeeDao() }
    var employee by remember { mutableStateOf<Employee?>(null) }

    LaunchedEffect(employeeName) {
        employeeName?.let {
            employeeDao.searchEmployeesByName(it).collect { emp ->
                employee = emp
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small3)
    ) {
        if (employee != null) {
            Text(text = "ID: ${employee?.id}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Full Name: ${employee?.fullName}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Job Title: ${employee?.jobTitle}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Department: ${employee?.department}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Gender: ${employee?.gender}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Ethnicity: ${employee?.ethnicity}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Age: ${employee?.age}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Hire Date: ${employee?.hireDate}", style = MaterialTheme.typography.labelMedium)
            Text(text = "Salary: ${employee?.salary}", style = MaterialTheme.typography.labelMedium)
        } else {
            CircularProgressIndicator()
            Text(text = stringResource(R.string.no_employee_details), style = MaterialTheme.typography.labelMedium)
        }
    }
}
