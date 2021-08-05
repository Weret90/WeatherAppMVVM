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
import com.umbrella.weatherappmvvp.view.adapters.CitiesAdapter
import com.umbrella.weatherappmvvp.viewmodel.MainActivityViewModel

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainActivityViewModel
    private var adapter: CitiesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
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

        if (adapter == null) {
            initAdapterAndMakeApiCalls()
        } else {
            binding.recyclerViewCities.adapter = adapter
            binding.loadingLayout.visibility = View.GONE
        }
    }

    private fun initAdapterAndMakeApiCalls() {
        viewModel.getCitiesLiveData().observe(viewLifecycleOwner, {
            adapter?.setCities(it)
            binding.loadingLayout.visibility = View.GONE
        })
        adapter = CitiesAdapter()
        binding.recyclerViewCities.adapter = adapter
        viewModel.makeApiCall("55.75", "37.61", "Москва")
        viewModel.makeApiCall("59.93", "30.33", "Санкт-Петербург")
        viewModel.makeApiCall("62.03", "129.67", "Якутск")
        adapter?.setOnCityClickListener {
            val bundle = Bundle()
            bundle.putSerializable("city", it)
            findNavController().navigate(R.id.cityInfoFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}