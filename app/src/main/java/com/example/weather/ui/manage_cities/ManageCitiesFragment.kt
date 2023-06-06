package com.example.weather.ui.manage_cities

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
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.domain.models.cities.City
import com.example.weather.databinding.FragmentManageCitiesBinding
import com.example.weather.ui.State
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageCitiesFragment : Fragment() {
    private var _binding: FragmentManageCitiesBinding? = null
    private val binding: FragmentManageCitiesBinding get() = _binding!!
    private val viewModel: ManageCitiesViewModel by viewModels()
    private lateinit var epoxyController: CitiesEpoxyController
    private var currentCity: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setUpActionBar()
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
        binding.fabOpenMapFragment.setOnClickListener {
            findNavController().navigate(ManageCitiesFragmentDirections.actionManageCitiesFragmentToMapFragment(currentCity))
        }
    }

    private fun getTopCities() {
        viewModel.getTopCities()
    }

    private fun setUpActionBar() {
        (requireActivity() as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left_black)
    }

    private fun observeViewModel() {
        viewModel.currentCity.observe(viewLifecycleOwner) {
            currentCity = Gson().toJson(it, City::class.java)
        }

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
        epoxyController = CitiesEpoxyController(
            context = requireContext(),
            onItemClicked = {
                viewModel.setCurrentCity(it)
                Toast.makeText(
                    requireContext(),
                    "You will get weather data for the selected city",
                    Toast.LENGTH_SHORT
                ).show()
//                findNavController().navigateUp()
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