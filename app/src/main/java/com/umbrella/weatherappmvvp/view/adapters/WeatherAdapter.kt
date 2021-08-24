package com.umbrella.weatherappmvvp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.databinding.CityItemBinding
import com.umbrella.weatherappmvvp.model.WeatherInCity
import kotlin.collections.ArrayList

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.CitiesViewHolder>() {

    private var weatherInCities: List<WeatherInCity> = ArrayList()
    private var onItemClick: (WeatherInCity) -> Unit = {}

    fun setData(weatherInCities: List<WeatherInCity>) {
        this.weatherInCities = weatherInCities
        notifyDataSetChanged()
    }

    fun getData() = weatherInCities

    fun setOnCityClickListener(onCityClick: (WeatherInCity) -> Unit) {
        this.onItemClick = onCityClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val binding = CityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CitiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(weatherInCities[position])
    }

    override fun getItemCount(): Int {
        return weatherInCities.size
    }

    inner class CitiesViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherInCity: WeatherInCity) {
            with(binding) {
                textViewName.text = weatherInCity.city?.name
                textViewStatus.text = weatherInCity.current.weather[0].description
                textViewTemperature.text = String.format(
                    root.resources.getString(R.string.temperature_celsius),
                    weatherInCity.current.temp.toString()
                )
                val icon = weatherInCity.current.weather[0].icon
                val iconUrl = String.format(root.resources.getString(R.string.icon_url_path), icon)
                Picasso.get()
                    .load(iconUrl)
                    .into(iconImage)

                root.setOnClickListener {
                    onItemClick(weatherInCity)
                }
            }
        }
    }
}