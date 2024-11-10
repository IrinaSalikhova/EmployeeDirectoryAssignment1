package com.example.employeedirectoryassignment1.weatherApp

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.employeedirectoryassignment1.ui.theme.Black
import com.example.employeedirectoryassignment1.ui.theme.dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun WeatherScreen(navController: NavHostController) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    val context = LocalContext.current

    val viewModel: WeatherViewModel = viewModel()

    val requestedCity = getCityFromPreferences(context)

    viewModel.fetchWeatherData(requestedCity.lat, requestedCity.lon, context.getString(R.string.weather_api))

    val weatherData = viewModel.weatherData
    Log.i("WeatherScreen", weatherData.toString())

    // Create a scroll state
    val scrollState = rememberScrollState()
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.medium2)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherData?.let { data ->
            // Display current weather
            CurrentWeatherView(data.current)

            // Display daily forecast excluding the current day
            DailyForecastView(data.daily.drop(1)) // Exclude the first item (current day)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back button
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
}


@Composable
fun CurrentWeatherView(currentWeather: CurrentWeather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = MaterialTheme.dimens.medium2)
    ) {
        Text(
            text = " ${currentWeather.weather[0].description}",
            style = MaterialTheme.typography.bodyMedium,
            color = isSystemInDarkTheme().let { if (it) Color.White else Color.Black }
        )
        // Load and display the weather icon
        val iconCode = currentWeather.weather[0].icon
        val iconUrl = "http://openweathermap.org/img/wn/${iconCode}@2x.png"
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = currentWeather.weather[0].description,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 3.dp))

        Text(
            text = "${currentWeather.temp}°C",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = currentWeather.weather.firstOrNull()?.description ?: "No description",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Humidity: ${currentWeather.humidity}%",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Wind Speed: ${currentWeather.wind_speed} m/s",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Pressure: ${currentWeather.pressure} hPa",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Feels Like: ${currentWeather.feels_like}°C",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Humidity: ${currentWeather.humidity}%",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Wind Speed: ${currentWeather.wind_speed} m/s",
            style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Composable
fun DailyForecastView(dailyWeather: List<DailyWeather>) {
    Column {
        Text(
            text = "Daily Forecast",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = MaterialTheme.dimens.small3)
        )
        // Limit to the first 5 days of the forecast
        val limitedDailyWeather = dailyWeather.take(5)

        limitedDailyWeather.forEach { day ->
            DailyWeatherItem(day)
        }
    }
}

@Composable
fun DailyWeatherItem(day: DailyWeather) {
    val dateFormatter = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
    val date = dateFormatter.format(Date(day.dt * 1000)) // Convert seconds to milliseconds

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.small1)
    ) {
        // Load and display the weather icon for the daily forecast
        Text(
            text = " ${day.weather[0].description}",
            style = MaterialTheme.typography.bodyMedium,
            color = isSystemInDarkTheme().let { if (it) Color.White else Color.Black }
        )
        // Load and display the weather icon
        val iconCode = day.weather[0].icon
        val iconUrl = "http://openweathermap.org/img/wn/${iconCode}@2x.png"
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = day.weather[0].description,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = "Day: ${day.temp.day}°C, Night: ${day.temp.night}°C")
        Text(text = "Weather: ${day.weather.firstOrNull()?.description ?: "No description"}")

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