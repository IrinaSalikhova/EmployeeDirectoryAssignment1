package com.example.employeedirectoryassignment1.weatherApp

data class CityResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)
