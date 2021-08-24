package com.umbrella.weatherappmvvp.model.network

import com.umbrella.weatherappmvvp.BuildConfig
import com.umbrella.weatherappmvvp.model.WeatherInCity
import retrofit2.http.GET
import retrofit2.http.Query

private const val apiKey = BuildConfig.WEATHER_API_KEY

interface RetroService {

    @GET("data/2.5/onecall?appid=$apiKey&lang=ru&units=metric")
    suspend fun getDataFromApi(@Query("lat") lat: String, @Query("lon") long: String): WeatherInCity
}