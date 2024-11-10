package com.example.employeedirectoryassignment1.weatherApp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("geo/1.0/direct")
    suspend fun getCityList(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 7,
        @Query("appid") apiKey: String
    ): List<CityResponse>


    // New API call to fetch current weather and forecast
    @GET("data/3.0/onecall")
    suspend fun getWeatherAndForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("units") units: String = "metric",  // Default to metric units
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

}


object RetrofitInstance {
    private const val BASE_URL = "http://api.openweathermap.org/"

    val api: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}