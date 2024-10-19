package com.example.employeedirectoryassignment1.weatherApp

data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val current: CurrentWeather,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>
)

data class DailyWeather(
    val dt: Long,
    val temp: Temperature,
    val weather: List<Weather>,
    val wind_speed: Double,
    val humidity: Int,
    val clouds: Int,
    val pop: Double  // Probability of precipitation
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
