package com.example.employeedirectoryassignment1.ITSupport

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.buttonContainer
import com.example.employeedirectoryassignment1.ui.theme.buttonText
import com.example.employeedirectoryassignment1.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(navController: NavHostController) {

    //Hung

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.techfix)) }
            )
        },
        content = { padding ->
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .height(MaterialTheme.dimens.buttonHeight),
                onClick = {
                    navController.navigate("MapView")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.buttonContainer,
                    contentColor = MaterialTheme.colorScheme.buttonText
                ),
                shape = RoundedCornerShape(size = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.go_to_map),
                    style = MaterialTheme
                        .typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
    )
}