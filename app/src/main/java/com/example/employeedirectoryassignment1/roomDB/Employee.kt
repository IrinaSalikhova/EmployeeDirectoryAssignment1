package com.example.employeedirectoryassignment1.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val jobTitle: String,
    val department: String,
    val gender: String,
    val ethnicity: String,
    val age: Int,
    val hireDate: String,
    val salary: Int

)
