package com.example.weather.ui.cities_list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.databinding.FragmentCitiesListBinding
import com.example.weather.ui.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CitiesListFragment : Fragment() {
    private var _binding: FragmentCitiesListBinding? = null
    private val binding: FragmentCitiesListBinding get() = _binding!!
    private val viewModel: CitiesListViewModel by viewModels()
    private lateinit var epoxyController: CitiesListEpoxyController

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
        observeViewModel()
        setUpEpoxyRecyclerView()
        getTopCities()
        searchCity()

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("CitiesListFragment", "viewModel.searchQuery: ${viewModel.searchQuery}")
                viewModel.searchCity(viewModel.searchQuery ?: "")
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.etSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH
    }

    private fun getTopCities() {
        viewModel.getTopCities()
    }

    private fun observeViewModel() {
        viewModel.citiesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    epoxyController.topCities = state.data!!.cities
                }

                is State.Loading -> {}
                is State.Error -> {}
            }
        }

        viewModel.searchCityState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    epoxyController.searchResultCities = state.data!!.foundCities
                    // to wait until the epoxy model is updated
                    Handler(Looper.getMainLooper()).postDelayed({
                        hideLoading()
                        hideError()
                    }, 500L)
                }

                is State.Error -> {
                    hideLoading()
                    displayError()
                }

                is State.Loading -> {
                    hideError()
                    displayLoading()
                }
            }
        }
    }

    private fun searchCity() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                it?.let { query ->
                    if (query.isEmpty()) {
                        hideError()
                        epoxyController.searchResultCities = emptyList()
                    }
                    viewModel.searchQuery = query.toString()
                    Log.d("CitiesListFragment", "viewModel.searchQuery: ${viewModel.searchQuery}")
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        binding.etSearch.clearFocus()
    }

    private fun setUpEpoxyRecyclerView() {
        epoxyController = CitiesListEpoxyController(
            context = requireContext(),
            onItemClicked = {
                viewModel.setCurrentCity(it)
                Toast.makeText(
                    requireActivity(),
                    "You will get weather data for the selected city",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        )
        binding.rvCities.setController(epoxyController)
    }

    private fun displayError() {
        binding.rvCities.visibility = View.INVISIBLE
        binding.tvMessageNoResults.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.rvCities.visibility = View.VISIBLE
        binding.tvMessageNoResults.visibility = View.INVISIBLE
    }

    private fun displayLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}