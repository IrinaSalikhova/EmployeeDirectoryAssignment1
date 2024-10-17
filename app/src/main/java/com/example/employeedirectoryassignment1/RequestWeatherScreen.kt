package com.example.employeedirectoryassignment1

import android.content.res.Configuration
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
fun RequestWeatherScreen (navController: NavHostController) {

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
                RequestSection(navController)
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
        RequestSection(navController)

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
            Text(
                text = stringResource(R.string.weather_application),
                style = MaterialTheme.typography.headlineMedium,
                color = uiColor
            )
        }
    }
}

@Composable
private fun RequestSection(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black

    var cityNameValue by remember { mutableStateOf("") }
    val cityNameRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimens.medium1)
    ) {

        LoginTextField(
            label = "City name",
            trailing = "Previous searches",
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(cityNameRequester),
            value = cityNameValue,
            onValueChange = {newValue -> cityNameValue = newValue}
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.buttonHeight),
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.buttonContainer,
                contentColor = MaterialTheme.colorScheme.buttonText
            ),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.weather_application),
                style = MaterialTheme
                    .typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            TextButton(onClick = {
                navController.navigate("navigationScreen")
            }) {
                Text(
                    text = "Go back to assignment selection",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = uiColor
                )
            }
        }
    }
}
