package com.example.employeedirectoryassignment1.roomDB

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EmployeeViewModelFactory(private val employeeDao: EmployeeDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            return EmployeeViewModel(employeeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}