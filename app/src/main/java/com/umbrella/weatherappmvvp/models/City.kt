package com.umbrella.weatherappmvvp.models

import java.io.Serializable

data class City(
    var name: String = "city", val current: CurrentInfo, val daily: List<Day>? = null
) : Serializable

data class CurrentInfo(
    val temp: Double, val weather: List<Weather>? = null
)

data class Weather(
    val description: String, val icon: String
)

data class Day(
    val dt: Int, val temp: DayTemperatures, val weather: List<Weather>? = null
)

data class DayTemperatures(
    val day: Double, val night: Double
)

