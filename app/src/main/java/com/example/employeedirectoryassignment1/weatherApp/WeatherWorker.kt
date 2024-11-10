package com.example.employeedirectoryassignment1.weatherApp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.employeedirectoryassignment1.R
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val sharedPreferences = context.getSharedPreferences(
        "city_prefs",
        Context.MODE_PRIVATE
    )

    override suspend fun doWork(): Result {
        return try {
            val city = sharedPreferences.getString("cityName", "") ?: ""
            if (city.isNotEmpty()) {
                val response = RetrofitInstance.api.getCityList(
                    cityName = city,
                    apiKey = R.string.weather_api.toString()
                )
                // Store the updated weather data
                updateLocalWeatherData(response)
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }


    private fun updateLocalWeatherData(weatherResponse: List<CityResponse>) {
        val gson = Gson()
        sharedPreferences.edit()
            .putString("weather_data", gson.toJson(weatherResponse))
            .putLong("last_update", System.currentTimeMillis())
            .apply()
    }
}