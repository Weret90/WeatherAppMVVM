package com.umbrella.weatherappmvvp.model

sealed class AppState {
    data class Success(val weatherData: List<WeatherInCity>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}