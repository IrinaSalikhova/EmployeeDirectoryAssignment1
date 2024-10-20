package com.example.employeedirectoryassignment1.employeeDirectory

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.Black
import com.example.employeedirectoryassignment1.ui.theme.dimens
import com.example.employeedirectoryassignment1.ui.theme.focusedTextFieldText
import com.example.employeedirectoryassignment1.ui.theme.textFieldContainer
import com.example.employeedirectoryassignment1.ui.theme.unfocusedTextFieldText
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    val context = LocalContext.current
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            delay(3000)  // Display the error message for 3 seconds
            errorMessage = null
        }
    }

    errorMessage?.let {
        Text(text = it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = uiColor)
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
        ),
        trailingIcon = {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                TextButton(onClick = {
                    errorMessage = context.getString(R.string.not_supported)
                }) {
                    Text(
                        text = trailing,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                        color = uiColor
                    )
                }
            }
        }
    )
}