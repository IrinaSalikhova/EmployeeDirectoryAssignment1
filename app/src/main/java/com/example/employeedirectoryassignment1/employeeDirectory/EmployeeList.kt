package com.example.employeedirectoryassignment1.employeeDirectory

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.roomDB.EmployeeDatabase
import com.example.employeedirectoryassignment1.roomDB.EmployeeViewModel
import com.example.employeedirectoryassignment1.roomDB.EmployeeViewModelFactory
import com.example.employeedirectoryassignment1.ui.theme.dimens


@Composable
fun EmployeeCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.dimens.small1)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimens.medium3)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun EmployeeList(navController: NavHostController) {
    val context = LocalContext.current
    val employeeDao = remember { EmployeeDatabase.getDatabase(context).employeeDao() }
    val viewModel: EmployeeViewModel = viewModel(factory = EmployeeViewModelFactory(employeeDao))

    Log.d("EmployeeList", "EmployeeList Composable called")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small3)
    ) {
        if (viewModel.employeeNamesList.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Log.d("EmployeeList", "Employee names list is populated")
            LazyColumn(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(MaterialTheme.dimens.small2)
            ) {
                items(viewModel.employeeNamesList) { name ->
                    EmployeeCard(name = name) {
                        navController.navigate("employeeDetailScreen/$name")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))

        Button(
            onClick = { navController.navigate("loginScreen") },
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.buttonHeight)
        ) {
            Text(stringResource(R.string.log_out))
        }
    }
}





