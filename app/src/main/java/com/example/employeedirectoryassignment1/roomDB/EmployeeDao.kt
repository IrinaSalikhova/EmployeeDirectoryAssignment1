package com.example.employeedirectoryassignment1.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT fullName FROM Employees")
    fun getAllEmployeesNames(): Flow<List<String>>

    @Query("SELECT * FROM Employees")
    fun getAllEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM Employees WHERE id = :id")
    suspend fun getEmployeeById(id: String): Employee?

    @Query("SELECT * FROM Employees WHERE fullName = :query LIMIT 1")
    fun searchEmployeesByName(query: String): Flow<Employee?>


}