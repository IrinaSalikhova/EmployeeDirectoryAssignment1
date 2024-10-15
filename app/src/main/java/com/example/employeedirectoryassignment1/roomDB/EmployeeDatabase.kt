package com.example.employeedirectoryassignment1.roomDB

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao


    companion object {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(context: Context): EmployeeDatabase {
            return INSTANCE ?: synchronized(this) {
                Log.d("EmployeeDatabase", "Creating new instance of EmployeeDatabase")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employee_database"
                    )
                    .addCallback(object :RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("EmployeeDatabase", "Database created, populating with initial data")
                            CoroutineScope(Dispatchers.IO).launch {
                                val employeeDao: EmployeeDao = INSTANCE!!.employeeDao()
                                val employeeList: JSONArray = context.assets
                                    .open("EmployeeData.json").bufferedReader().use {
                                        JSONArray(it.readText())
                                    }
                                employeeList.takeIf { it.length() > 0 }?.let { list ->
                                    for (index in 0 until list.length()){
                                        val employeeObj = list.getJSONObject(index)
                                        employeeDao.insertEmployee(
                                            Employee(
                                                fullName = employeeObj.getString("fullName"),
                                                jobTitle = employeeObj.getString("jobTitle"),
                                                department = employeeObj.getString("department"),
                                                gender = employeeObj.getString("gender"),
                                                ethnicity = employeeObj.getString("ethnicity"),
                                                age = employeeObj.getInt("age"),
                                                hireDate = employeeObj.getString("hireDate"),
                                                salary = employeeObj.getInt("salary")
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
                }
        }
    }
}

