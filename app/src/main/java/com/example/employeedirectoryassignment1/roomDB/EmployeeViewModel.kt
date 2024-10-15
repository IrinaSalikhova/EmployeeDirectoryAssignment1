package com.example.employeedirectoryassignment1.roomDB

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class EmployeeViewModel(private val employeeDao: EmployeeDao) : ViewModel() {
    private val _employeeNamesList = mutableStateListOf<String>()
    val employeeNamesList: List<String> get() = _employeeNamesList


    init {
        Log.d("EmployeeViewModel", "ViewModel initialized, fetching employee names")
        fetchEmployeeNames()
    }

    private fun fetchEmployeeNames() {
        viewModelScope.launch {
            employeeDao.getAllEmployeesNames().collectLatest { names ->
                Log.d("EmployeeViewModel", "Received employee names: ${names.joinToString()}")
                _employeeNamesList.clear()
                _employeeNamesList.addAll(names)
            }
        }
    }
}