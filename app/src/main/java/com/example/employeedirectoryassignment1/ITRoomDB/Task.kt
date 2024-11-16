package com.example.employeedirectoryassignment1.ITRoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientFirstName: String,
    val clientLastName: String,
    val address: String,
    val lon: Double,
    val lat: Double,
    val isDone: Boolean
)

