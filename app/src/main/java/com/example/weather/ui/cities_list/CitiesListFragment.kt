package com.example.weather.ui.cities_list

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentCitiesListBinding
import com.example.weather.ui.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesListFragment : Fragment() {
    private var _binding: FragmentCitiesListBinding? = null
    private val binding: FragmentCitiesListBinding get() = _binding!!
    private val viewModel: CitiesListViewModel by viewModels()
    private val epoxyController = CitiesListEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEpoxyRecyclerView()
        viewModel.getTopCities()
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    epoxyController.cities = state.data!!.cities
                }

                is State.Loading -> {

                }

                is State.Error -> {

                }
            }
        }
    }

    private fun setUpEpoxyRecyclerView() {
        binding.rvCities.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}