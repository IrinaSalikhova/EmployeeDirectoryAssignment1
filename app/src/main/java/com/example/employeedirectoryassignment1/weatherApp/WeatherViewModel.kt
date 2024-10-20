package com.example.employeedirectoryassignment1.weatherApp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _cityList = MutableStateFlow<List<CityResponse>>(emptyList())
    val cityList: StateFlow<List<CityResponse>> get() = _cityList

    var errorMessage by mutableStateOf<String?>(null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchCityList(cityName: String, limit: Int, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getCityList(cityName, limit, apiKey)
                _cityList.value = response
            } catch (e: Exception) {
                errorMessage = "Network Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    var weatherData: WeatherResponse? = null

    fun fetchWeatherData(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> =
                    RetrofitInstance.api.getWeatherAndForecast(
                        lat = lat,
                        lon = lon,
                        apiKey = apiKey
                    )
                if (response.isSuccessful) {
                    weatherData = response.body()
                } else {
                    errorMessage = "Network Error: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Network Error: ${e.message}"
            }
        }
    }
}
