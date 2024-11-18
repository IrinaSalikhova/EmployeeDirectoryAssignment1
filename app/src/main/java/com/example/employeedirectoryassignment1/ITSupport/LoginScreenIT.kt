package com.example.employeedirectoryassignment1.ITSupport

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.buttonContainer
import com.example.employeedirectoryassignment1.ui.theme.buttonText
import com.example.employeedirectoryassignment1.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenIT(navController: NavHostController, context: Context) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.techfix)) }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(MaterialTheme.dimens.buttonHeight),
                    onClick = {
                        when {
                            username.isEmpty() || password.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    "The User Name/Password cannot be empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            username == "ken" && password == "1234" -> {
                                // Save username in SharedPreferences
                                val sharedPreferences = context.getSharedPreferences(
                                    "login",
                                    Context.MODE_PRIVATE
                                )
                                sharedPreferences.edit().putString("username", username).apply()

                                Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                                navController.navigate("ITTaskList")
                            }
                            else -> {
                                Toast.makeText(
                                    context,
                                    "Invalid username or password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.buttonContainer,
                        contentColor = MaterialTheme.colorScheme.buttonText
                    ),
                    shape = RoundedCornerShape(size = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme
                            .typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    )
}