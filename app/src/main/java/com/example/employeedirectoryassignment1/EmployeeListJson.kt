package com.example.employeedirectoryassignment1

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.ui.theme.dimens
import org.json.JSONArray
import org.json.JSONObject


@Composable
fun EmployeeListJson(navController: NavHostController) {
    val context = LocalContext.current
    val employees = remember {
        mutableStateListOf<JSONObject>()
    }

    LaunchedEffect(Unit) {
        val employeeList = readJson(context)
        employees.clear()
        employees.addAll(employeeList)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small3)
    ) {
        LazyColumn(
            modifier = Modifier.weight(0.8f)
        ) {

            items(employees) { employee ->
                val employeeName = employee.getString("fullName")
                EmployeeCard(name = employeeName) {
                    saveEmployeeToPreferences(context, employee)
                    navController.navigate("employeeDetailScreen")
                }
            }
        }


        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))

        // Logout Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.buttonHeight)
        ) {
            Text(stringResource(R.string.log_out))
        }
    }
}



fun readJson(applicationContext: Context): List<JSONObject> {
    val employeeList: MutableList<JSONObject> = mutableListOf()
    val jsonArray: JSONArray = applicationContext.assets
        .open("EmployeeData.json").bufferedReader().use {
            JSONArray(it.readText())
        }
    jsonArray.takeIf { it.length() > 0 }?.let { list ->
        for (index in 0 until list.length()) {  // Loop through the actual length of the array
            val employeeObj = list.getJSONObject(index)
            employeeList.add(employeeObj)
        }
    }
    return employeeList // Return the list of names

}

fun saveEmployeeToPreferences(context: Context, employee: JSONObject) {
    val sharedPreferences = context.getSharedPreferences("employee_prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("fullName", employee.getString("fullName"))
        putString("jobTitle", employee.getString("jobTitle"))
        putString("department", employee.getString("department"))
        putString("gender", employee.getString("gender"))
        putString("ethnicity", employee.getString("ethnicity"))
        putInt("age", employee.getInt("age"))
        putString("hireDate", employee.getString("hireDate"))
        putInt("salary", employee.getInt("salary"))
        apply()
    }
}
