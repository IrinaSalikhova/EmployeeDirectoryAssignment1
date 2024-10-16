package com.example.employeedirectoryassignment1

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.ui.theme.Black
import com.example.employeedirectoryassignment1.ui.theme.buttonContainer
import com.example.employeedirectoryassignment1.ui.theme.buttonText
import com.example.employeedirectoryassignment1.ui.theme.dimens


@Composable
fun LoginScreen(navController: NavHostController) {

    Surface {
        if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            PortraitLoginScreen(navController)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.dimens.medium1),
                verticalArrangement = Arrangement.Center
            ) {
                LoginSection(navController)
            }
        }
    }
}

@Composable
private fun PortraitLoginScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopSection()
        Spacer(
            modifier = Modifier.height(MaterialTheme.dimens.medium2)
        )
        LoginSection(navController)

    }
}

@Composable
private fun TopSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.43f),
            painter = painterResource(R.drawable.shape),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier.padding(top = MaterialTheme.dimens.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.medium3),
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.app_logo),
                tint = uiColor
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))
            Column {
                Text(
                    text = stringResource(R.string.company_name),
                    style = MaterialTheme.typography.headlineMedium,
                    color = uiColor
                )
                Text(
                    text = stringResource(R.string.department_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimens.small1)
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )
    }
}

@Composable
private fun LoginSection(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black

    var isSignUpMode by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var loginFieldValue by remember { mutableStateOf("") }
    var passwordFieldValue by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successfulMessage by remember { mutableStateOf<String?>(null) }

    val loginFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimens.medium1)
    ) {
        successfulMessage?.let {
            Text(text = it, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
        }

        LoginTextField(
            label = stringResource(R.string.email),
            trailing = "",
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(loginFocusRequester),
            value = loginFieldValue,
            onValueChange = {newValue -> loginFieldValue = newValue}
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.dimens.small2)
        )
        LoginTextField(
            label = stringResource(R.string.password),
            trailing = stringResource(R.string.forgot_password),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            value = passwordFieldValue,
            onValueChange = {newValue -> passwordFieldValue = newValue}
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.dimens.small2)
        )
        errorMessage?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.buttonHeight),
            onClick = {
                errorMessage = null
                if (isSignUpMode) {
                    if (loginFieldValue.isEmpty()) {
                        errorMessage = context.getString(R.string.empty_login_message)
                        loginFocusRequester.requestFocus()
                    } else if (passwordFieldValue.isEmpty()) {
                        errorMessage = context.getString(R.string.empty_password_message)
                        passwordFocusRequester.requestFocus()
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(loginFieldValue).matches()) {
                        errorMessage = context.getString(R.string.invalid_email)
                        loginFocusRequester.requestFocus()
                    }
                    else if (passwordFieldValue.length < 8 ||
                        !passwordFieldValue.matches(".*[A-Z].*".toRegex()) ||
                        !passwordFieldValue.matches(".*[a-z].*".toRegex()) ||
                        !passwordFieldValue.matches(".*[0-9].*".toRegex()) ||
                        !passwordFieldValue.matches(".*[!@#\$%^&+=].*".toRegex())
                        ) {
                        errorMessage = context.getString(R.string.invalid_password)
                        passwordFocusRequester.requestFocus()
                    } else {
                        setupLoginCredentials(context, loginFieldValue, passwordFieldValue)
                        successfulMessage = context.getString(R.string.success_register)
                        isSignUpMode = false
                    }
                } else {
                    if (isLoginValid(context, loginFieldValue, passwordFieldValue)) {
                        navController.navigate("employeeListScreen")
                    } else if (loginFieldValue.isEmpty()) {
                        errorMessage = context.getString(R.string.empty_login_message)
                        loginFocusRequester.requestFocus()
                    } else if (passwordFieldValue.isEmpty()) {
                        errorMessage = context.getString(R.string.empty_password_message)
                        passwordFocusRequester.requestFocus()
                    } else {
                        errorMessage = context.getString(R.string.invalid_message)
                        loginFocusRequester.requestFocus()
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
                text = if (isSignUpMode) stringResource(R.string.sign_up) else stringResource(R.string.login),
                style = MaterialTheme
                    .typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
        Spacer(
            modifier = Modifier.height(MaterialTheme.dimens.medium3)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            TextButton(onClick = {
                if (isSignUpMode) {
                    isSignUpMode = false
                    errorMessage = null
                    successfulMessage = null
                } else {
                    isSignUpMode = true
                    errorMessage = null
                    successfulMessage = context.getString(R.string.to_register)
                }
            }) {
                Text(
                    text = if (isSignUpMode) stringResource(R.string.to_login) else stringResource(R.string.to_reg),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = uiColor
                )
            }
        }
    }
}


fun setupLoginCredentials(context: Context, login: String, password: String) {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("login", login)
        putString("password", password)
        apply()
        Log.d("LoginScreen", "Hardcoded credentials applied")
    }
}

fun isLoginValid(context: Context, enteredUsername: String, enteredPassword: String): Boolean {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val storedUsername = sharedPref.getString("login", "")
    val storedPassword = sharedPref.getString("password", "")

    val boolean = enteredUsername == storedUsername && enteredPassword == storedPassword
    Log.d("LoginScreen", (boolean.toString() + "isLoginValid" + enteredUsername + storedUsername + enteredPassword + storedPassword ))
    return boolean
}
