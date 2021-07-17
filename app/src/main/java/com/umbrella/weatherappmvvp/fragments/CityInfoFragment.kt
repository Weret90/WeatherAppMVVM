package com.umbrella.weatherappmvvp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.models.City
import java.text.SimpleDateFormat
import java.util.*

class CityInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val city = it.getSerializable("city") as City
            val cityName = city.name
            val currentStatus = city.current.weather?.get(0)?.description
            val currentTemp = city.current.temp.toString() + "°С"
            val tomorrowDay = unixTimeConverter(city.daily?.get(1)?.dt)
            val tomorrowTemp = city.daily?.get(1)?.temp?.day.toString() + "°С"
            val tomorrowStatus = city.daily?.get(1)?.weather?.get(0)?.description
            val afterTomorrowDay = unixTimeConverter(city.daily?.get(2)?.dt)
            val afterTomorrowTemp = city.daily?.get(2)?.temp?.day.toString() + "°С"
            val afterTomorrowStatus = city.daily?.get(2)?.weather?.get(0)?.description
            val icon = city.current.weather?.get(0)?.icon
            val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"

            Picasso.get()
                .load(iconUrl)
                .into(view.findViewById<ImageView>(R.id.iconBigImage))

            view.findViewById<TextView>(R.id.cityName).text = cityName
            view.findViewById<TextView>(R.id.currentTemperature).text = currentTemp
            view.findViewById<TextView>(R.id.currentStatus).text = currentStatus
            view.findViewById<TextView>(R.id.tomorrowDay).text = tomorrowDay
            view.findViewById<TextView>(R.id.tomorrowTemperature).text = tomorrowTemp
            view.findViewById<TextView>(R.id.tomorrowStatus).text = tomorrowStatus
            view.findViewById<TextView>(R.id.afterTomorrowDay).text = afterTomorrowDay
            view.findViewById<TextView>(R.id.afterTomorrowTemperature).text = afterTomorrowTemp
            view.findViewById<TextView>(R.id.afterTomorrowStatus).text = afterTomorrowStatus
        }
    }

    private fun unixTimeConverter(unixTime: Int?): String {
        val sdf = SimpleDateFormat("dd.MM")
        val date = Date(unixTime!!.toLong() * 1000)
        return sdf.format(date)
    }
}