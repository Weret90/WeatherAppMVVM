package com.umbrella.weatherappmvvp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.models.City
import kotlin.collections.ArrayList

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    private var cities: List<City> = ArrayList()

    private var onCityClickListener: OnCityClickListener? = null

    fun updateData(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    interface OnCityClickListener {
        fun onCityClick(position: Int)
    }

    fun setOnCityClickListener(onCityClickListener: OnCityClickListener?) {
        this.onCityClickListener = onCityClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CitiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val city = cities[position]
        holder.textViewName.text = city.name
        holder.textViewStatus.text = city.current.weather?.get(0)?.description
        holder.textViewTemperature.text = city.current.temp.toString()
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    inner class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        val textViewTemperature: TextView = itemView.findViewById(R.id.textViewTemperature)

        init {
            itemView.setOnClickListener {
                if (onCityClickListener != null) {
                    onCityClickListener!!.onCityClick(adapterPosition)
                }
            }
        }
    }
}