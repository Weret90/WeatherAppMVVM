package com.umbrella.weatherappmvvp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbrella.weatherappmvvp.model.AppState
import com.umbrella.weatherappmvvp.model.WeatherInCity
import com.umbrella.weatherappmvvp.model.City
import com.umbrella.weatherappmvvp.model.network.RetroInstance
import com.umbrella.weatherappmvvp.model.network.RetroService
import kotlinx.coroutines.*

class MainActivityViewModel : ViewModel() {

    private val downloadStatusLiveData = MutableLiveData<AppState>()

    companion object {
        private val weatherList = ArrayList<WeatherInCity>()
        private val LOCK = Any()
        private var isError = false
    }

    fun getDownloadStatusLiveData() = downloadStatusLiveData

    private suspend fun makeApiCall(city: City) {
        try {
            val retroInstance =
                RetroInstance.getRetroInstance().create(RetroService::class.java)
            val response = retroInstance.getDataFromApi(city.lat, city.lon)
            response.city = city
            synchronized(LOCK) {
                weatherList.add(response)
            }
        } catch (e: Exception) {
            synchronized(LOCK) {
                if (!isError) {
                    downloadStatusLiveData.postValue(AppState.Error(e))
                    isError = true
                }
            }
        }
    }

    fun makeApiCalls(city1: City, city2: City, city3: City) {
        weatherList.clear()
        isError = false
        downloadStatusLiveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            joinAll(
                launch { makeApiCall(city1) },
                launch { makeApiCall(city2) },
                launch { makeApiCall(city3) }
            )
            if (weatherList.isNotEmpty() && !isError) {
                downloadStatusLiveData.postValue(AppState.Success(weatherList))
            }
        }
    }
}



