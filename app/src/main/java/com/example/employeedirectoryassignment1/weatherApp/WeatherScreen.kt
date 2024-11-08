package com.example.employeedirectoryassignment1.weatherApp

import android.content.Context
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.Black
import com.example.employeedirectoryassignment1.ui.theme.dimens

@Composable
fun WeatherScreen(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val context = LocalContext.current

    val viewModel: WeatherViewModel = viewModel()

    val requestedCity = getCityFromPreferences(context)

    viewModel.fetchWeatherData(requestedCity.lat, requestedCity.lon, context.getString(R.string.weather_api))

    val weatherData = viewModel.weatherData
    Log.i("WeatherScreen", weatherData.toString())





    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.small3),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = {
            navController.navigate("navigationScreen")
        }) {
            Text(
                text = stringResource(R.string.go_back),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = uiColor
            )
        }
    }
}

fun getCityFromPreferences(context: Context): CityResponse{
    val sharedPreferences = context.getSharedPreferences("city_prefs", Context.MODE_PRIVATE)
    val requestedCity = CityResponse(
    sharedPreferences.getString("cityName", "").toString(),
    sharedPreferences.getString("lat", "0")!!.toDouble(),
    sharedPreferences.getString("lon", "0")!!.toDouble(),
    sharedPreferences.getString("country", "").toString())
    return requestedCity
}