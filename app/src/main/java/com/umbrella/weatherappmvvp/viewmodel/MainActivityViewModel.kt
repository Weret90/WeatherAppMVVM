package com.umbrella.weatherappmvvp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbrella.weatherappmvvp.models.City
import com.umbrella.weatherappmvvp.network.RetroInstance
import com.umbrella.weatherappmvvp.network.RetroService
import kotlinx.coroutines.*

class MainActivityViewModel : ViewModel() {

    val citiesLiveData = MutableLiveData<List<City>>()

    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = mutableListOf<City>()
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            joinAll(
                launch {
                    val response1 = retroInstance.getDataFromApi("55.75", "37.61")
                    response1.name = "Москва"
                    cities.add(response1)
                },
                launch {
                    val response2 = retroInstance.getDataFromApi("59.93", "30.33")
                    response2.name = "Санкт-Петербург"
                    cities.add(response2)
                },
                launch {
                    val response3 = retroInstance.getDataFromApi("62.03", "129.67")
                    response3.name = "Якутск"
                    cities.add(response3)
                }
            )
            citiesLiveData.postValue(cities)
        }
    }
}
