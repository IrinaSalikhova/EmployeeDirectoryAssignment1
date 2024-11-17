package com.example.employeedirectoryassignment1.ITRoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Insert
    suspend fun insertListofTasks(task: List<Task>)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Tasks")
    fun getAllTasks():Flow<List<Task>>

    @Query("SELECT * FROM Tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?
}