package com.example.employeedirectoryassignment1.weatherApp

import android.content.Context
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeedirectoryassignment1.R
import com.example.employeedirectoryassignment1.ui.theme.Black
import com.example.employeedirectoryassignment1.ui.theme.dimens
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.*

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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            weatherData?.daily?.take(5)?.forEach { dailyWeather ->
                // Convert Unix time to a readable date format
                val date = SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(Date(dailyWeather.dt * 1000))

                Text(
                    text = "Date: $date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = uiColor
                )
                Text(
                    text = "Day Temp: ${dailyWeather.temp.day}°C / Night Temp: ${dailyWeather.temp.night}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = uiColor
                )
                Text(
                    text = "Description: ${dailyWeather.weather[0].description}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = uiColor
                )
                // Load and display the weather icon
                val iconCode = dailyWeather.weather[0].icon
                val iconUrl = "http://openweathermap.org/img/wn/${iconCode}@2x.png"
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = dailyWeather.weather[0].description,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

        TextButton(
            onClick = { navController.navigate("requestWeatherScreen") },
            modifier = Modifier.padding(top= 500.dp)  // Adjust bottom padding as needed
        ) {
            Text(
                text = stringResource(R.string.select_city),
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

