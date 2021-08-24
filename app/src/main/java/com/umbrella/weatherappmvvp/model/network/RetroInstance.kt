package com.umbrella.weatherappmvvp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.openweathermap.org/"

class RetroInstance {
    companion object {
        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}