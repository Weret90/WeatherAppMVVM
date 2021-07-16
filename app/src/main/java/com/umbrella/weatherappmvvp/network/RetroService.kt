package com.umbrella.weatherappmvvp.network

import com.umbrella.weatherappmvvp.models.City
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("data/2.5/onecall?appid=67401936c26274f7e3d9b19dd82b101c&lang=ru&units=metric")
    suspend fun getDataFromApi(@Query("lat") lat: String, @Query("lon") long: String): City
}