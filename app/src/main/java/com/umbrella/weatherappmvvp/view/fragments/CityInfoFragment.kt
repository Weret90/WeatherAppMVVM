package com.umbrella.weatherappmvvp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.databinding.FragmentCityInfoBinding
import com.umbrella.weatherappmvvp.model.WeatherInCity
import java.text.SimpleDateFormat
import java.util.*

class CityInfoFragment : Fragment() {

    private var _binding: FragmentCityInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val weatherInCity = it.getSerializable("city") as WeatherInCity
            with(binding) {
                weatherInCity.apply {
                    nameOfCity.text = city?.name

                    currentTemperature.text = String.format(
                        root.resources.getString(R.string.temperature_celsius),
                        current.temp.toString()
                    )
                    tomorrowTemperature.text = String.format(
                        root.resources.getString(R.string.temperature_celsius),
                        daily[1].temp.day.toString()
                    )
                    afterTomorrowTemperature.text = String.format(
                        root.resources.getString(R.string.temperature_celsius),
                        daily[2].temp.day.toString()
                    )

                    currentStatus.text = current.weather[0].description
                    tomorrowStatus.text = daily[1].weather[0].description
                    afterTomorrowStatus.text = daily[1].weather[0].description

                    tomorrowDay.text = unixTimeConverter(daily[1].dt)
                    afterTomorrowDay.text = unixTimeConverter(daily[2].dt)

                    val icon = current.weather[0].icon
                    val iconUrl =
                        String.format(root.resources.getString(R.string.icon_url_path), icon)
                    Picasso.get()
                        .load(iconUrl)
                        .into(iconBigImage)
                }
            }
        }
    }

    private fun unixTimeConverter(unixTime: Int): String {
        val sdf = SimpleDateFormat("dd.MM")
        val date = Date(unixTime.toLong() * 1000)
        return sdf.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}