package com.example.employeedirectoryassignment1.ITRoomDB

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.employeedirectoryassignment1.ITRoomDB.TaskDatabase.Companion.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase()  {
    abstract fun taskDao(): TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // Singleton pattern to ensure only one instance of the database is created
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                )
                    .addCallback(PrepopulateCallback(context)) // Add pre-population callback
                    .fallbackToDestructiveMigration() // Handles migrations by recreating the database
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class PrepopulateCallback(context: Context) : RoomDatabase.Callback() {
    private val context = context
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Prepopulate the database
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val database = getDatabase(context)
            val dao = database.taskDao()
            populateDatabase(dao)
        }
    }

    private suspend fun populateDatabase(dao: TaskDao) {
        val sampleTasks = listOf(
            Task(clientFirstName = "Alice", clientLastName = "Smith", address = "564 Golden Ave, Ottawa", lon = -75.7583, lat = 45.3853, isDone = false),
            Task(clientFirstName = "Bob", clientLastName = "Johnson", address = "150 Elgin St, Ottawa", lon = -75.6894, lat = 45.4200, isDone = false),
            Task(clientFirstName = "Charlie", clientLastName = "Brown", address = "39 Parkglen Dr, Ottawa", lon = -75.7565, lat = 45.3467, isDone = false),
            Task(clientFirstName = "Diana", clientLastName = "White", address = "75 Laurier Ave E, Ottawa", lon = -75.6866, lat = 45.4239, isDone = false),
            Task(clientFirstName = "Eve", clientLastName = "Davis", address = "154 Shearer Crescent, Kanata", lon = -75.8852, lat = 45.3020, isDone = false)
        )
        dao.insertListofTasks(sampleTasks) // Insert all sample tasks
        Log.d("MapView", "Data inserted")
    }
}

