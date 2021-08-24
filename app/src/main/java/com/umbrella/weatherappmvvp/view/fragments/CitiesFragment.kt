package com.umbrella.weatherappmvvp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.umbrella.weatherappmvvp.R
import com.umbrella.weatherappmvvp.databinding.FragmentCitiesBinding
import com.umbrella.weatherappmvvp.model.AppState
import com.umbrella.weatherappmvvp.model.City
import com.umbrella.weatherappmvvp.view.adapters.WeatherAdapter
import com.umbrella.weatherappmvvp.view.hide
import com.umbrella.weatherappmvvp.view.show
import com.umbrella.weatherappmvvp.view.showSnackBar
import com.umbrella.weatherappmvvp.view.showToast
import com.umbrella.weatherappmvvp.viewmodel.MainActivityViewModel

private val city1 = City("Москва", "55.75", "37.61")
private val city2 = City("Санкт-Петербург", "59.93", "30.33")
private val city3 = City("Якутск", "62.03", "129.67")

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    private var weatherInCitiesAdapter = WeatherAdapter()

    companion object {
        const val ARG_CITY = "city"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherInCitiesAdapter.setOnCityClickListener {
            val bundle = Bundle()
            bundle.putSerializable(ARG_CITY, it)
            findNavController().navigate(R.id.cityInfoFragment, bundle)
        }

        binding.recyclerViewCities.adapter = weatherInCitiesAdapter

        if (weatherInCitiesAdapter.getData().isEmpty()) {
            initObserver()
            viewModel.makeApiCalls(city1, city2, city3)
        }
    }

    private fun initObserver() {
        viewModel.getDownloadStatusLiveData().observe(viewLifecycleOwner, { result ->
            with(binding) {
                when (result) {
                    is AppState.Loading -> {
                        loadingLayout.show()
                    }
                    is AppState.Success -> {
                        loadingLayout.hide()
                        appHeadline.text = getString(R.string.table_with_cities_headline_success)
                        weatherInCitiesAdapter.setData(result.weatherData)
                    }
                    is AppState.Error -> {
                        loadingLayout.hide()
                        appHeadline.text = getString(R.string.table_with_cities_headline_error)
                        root.showSnackBar(getString(R.string.error), getString(R.string.reload)) {
                            viewModel.makeApiCalls(city1, city2, city3)
                        }
                        root.showToast(result.error.toString())
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}