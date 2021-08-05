package com.umbrella.weatherappmvvp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.databinding.CityItemBinding
import com.umbrella.weatherappmvvp.model.City
import kotlin.collections.ArrayList

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    private var cities: List<City> = ArrayList()
    private var onCityClickListener: (City) -> Unit = {}

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    fun setOnCityClickListener(onCityClickListener: (City) -> Unit) {
        this.onCityClickListener = onCityClickListener
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
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    inner class CitiesViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(city: City) {
            with(binding) {
                textViewName.text = city.cityName
                textViewStatus.text = city.current.weather[0].description
                textViewTemperature.text = String.format(
                    root.resources.getString(R.string.temperature_celsius),
                    city.current.temp.toString()
                )
                val icon = city.current.weather[0].icon
                val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
                Picasso.get()
                    .load(iconUrl)
                    .into(iconImage)

                root.setOnClickListener {
                    onCityClickListener(city)
                }
            }
        }
    }
}