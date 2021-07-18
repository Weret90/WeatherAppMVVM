package com.umbrella.weatherappmvvp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.adapter.CitiesAdapter
import com.umbrella.weatherappmvvp.models.City
import com.umbrella.weatherappmvvp.viewmodel.MainActivityViewModel

class CitiesFragment : Fragment() {

    private lateinit var adapter: CitiesAdapter
    private var cities: List<City> = ArrayList()
    private lateinit var infoTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoTextView = view.findViewById(R.id.infoTextView)
        adapter = CitiesAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCities)
        recyclerView.adapter = adapter
        adapter.setOnCityClickListener(object : CitiesAdapter.OnCityClickListener {
            override fun onCityClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("city", cities[position])
                findNavController().navigate(R.id.cityInfoFragment, bundle)
            }
        })
        if (cities.isEmpty()) {
            initViewModel()
        } else {
            adapter.updateData(cities)
            infoTextView.visibility = View.GONE
        }
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.citiesLiveData.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                adapter.updateData(it)
                cities = it
                infoTextView.visibility = View.GONE
            }
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                infoTextView.text = "Ошибка получения данных. Проверьте интернет-соединение"
            }
        })
        viewModel.makeApiCall()
    }
}