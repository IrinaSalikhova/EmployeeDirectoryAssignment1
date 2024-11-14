package com.example.employeedirectoryassignment1.weatherApp

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.applicationWide.ScreenOrientation
import com.example.employeedirectoryassignment1.ui.theme.Black
import com.example.employeedirectoryassignment1.ui.theme.buttonContainer
import com.example.employeedirectoryassignment1.ui.theme.buttonText
import com.example.employeedirectoryassignment1.ui.theme.dimens
import com.example.employeedirectoryassignment1.ui.theme.focusedTextFieldText
import com.example.employeedirectoryassignment1.ui.theme.textFieldContainer
import com.example.employeedirectoryassignment1.ui.theme.unfocusedTextFieldText
import kotlinx.coroutines.delay

@Composable
fun RequestWeatherScreen(navController: NavHostController) {

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RequestSection(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val context = LocalContext.current

    val viewModel: WeatherViewModel = viewModel()

    var cityNameValue by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf(viewModel.errorMessage) }

    var dropdownExpanded by remember { mutableStateOf(false) }

    val cityList by viewModel.cityList.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    var selectedCity by remember { mutableStateOf<CityResponse?>(null) }

    val defaultCity = CityResponse(
        name = "Ottawa",
        lat = 45.4208777,
        lon = -75.6901106,
        country = "CA",
        state = "Ontario"
    )

    LaunchedEffect(cityNameValue) {
        delay(3000)
        if (cityNameValue.isNotEmpty() && selectedCity == null) {
            dropdownExpanded = false
            viewModel.fetchCityList(cityNameValue, 10, context.getString(R.string.weather_api))
            dropdownExpanded = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimens.medium1)
    ) {
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                delay(3000)  // Display the error message for 3 seconds
                errorMessage = null
            }
        }
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.errorMessage = null
        }

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = {
                dropdownExpanded = !dropdownExpanded
            }
        ) {
            // TextField inside ExposedDropdownMenuBox
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
                value = selectedCity?.let { "${it.name}, ${it.country}" } ?: cityNameValue,
                onValueChange = { newValue ->
                    cityNameValue = newValue
                    if (selectedCity != null) {
                        selectedCity = null
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.city_name),
                        style = MaterialTheme.typography.labelMedium,
                        color = uiColor
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
                    unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
                ),
                trailingIcon = {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        TextButton(onClick = {
                            errorMessage = context.getString(R.string.not_supported)
                        }) {
                            Text(
                                text = stringResource(R.string.previous_searches),
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                color = uiColor
                            )
                        }
                    }
                }
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                if (cityList.isNotEmpty()) {
                    cityList.forEach { city ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCity = city
                                cityNameValue = "${city.name}, ${city.country}"
                                dropdownExpanded = false
                            },
                            text = {
                                Text(
                                    text = "${city.name}, ${city.country}",
                                    color = uiColor
                                )
                            }
                        )
                    }
                }
            }

            // Show loading indicator if needed
            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(vertical = MaterialTheme.dimens.small3),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = {
                // Check if the selected city is valid
                val isCityValid = selectedCity != null || cityList.any { it.name.equals(cityNameValue, ignoreCase = true) }

                if (isCityValid) {
                    val cityToSave = selectedCity ?: cityList.find { it.name.equals(cityNameValue, ignoreCase = true) }
                    saveCityToPreferences(context, cityToSave!!)
                    navController.navigate("weatherScreen")
                } else {
                    Toast.makeText(context, R.string.invalid_city_name, Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(
                    text = stringResource(R.string.update_city),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = uiColor
                )
            }
        }
    }
}

fun saveCityToPreferences(context: Context, cityResponse: CityResponse) {
    val sharedPreferences = context.getSharedPreferences("city_prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("cityName", cityResponse.name)
        putString("lat", cityResponse.lat.toString())
        putString("lon", cityResponse.lon.toString())
        putString("country", cityResponse.country)
        apply()
    }
}
