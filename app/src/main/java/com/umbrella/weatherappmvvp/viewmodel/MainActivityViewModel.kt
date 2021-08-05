package com.umbrella.weatherappmvvp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbrella.weatherappmvvp.model.City
import com.umbrella.weatherappmvvp.model.network.RetroInstance
import com.umbrella.weatherappmvvp.model.network.RetroService
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivityViewModel : ViewModel() {

    private val citiesLiveDate = MutableLiveData<List<City>>()

    companion object {
        private val cities = ArrayList<City>()
        private val LOCK = Any()
        private const val CITIES_NUMBER = 3
        private var isError = false
    }

    fun getCitiesLiveData() = citiesLiveDate

    fun makeApiCall(lat: String, lon: String, cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (cities.size < CITIES_NUMBER) {
                    val retroInstance =
                        RetroInstance.getRetroInstance().create(RetroService::class.java)
                    val response = retroInstance.getDataFromApi(lat, lon)
                    response.cityName = cityName
                    synchronized(LOCK) {
                        cities.add(response)
                        if (cities.size == CITIES_NUMBER) {
                            citiesLiveDate.postValue(cities)
                        }
                    }
                }
            } catch (e: Exception) {
                synchronized(LOCK) {
                    if (!isError) {
                        isError = true
                        Log.i("proverka", e.toString())
                    }
                }
            }
        }
    }
}

