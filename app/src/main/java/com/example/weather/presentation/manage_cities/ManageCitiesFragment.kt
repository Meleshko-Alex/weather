package com.example.weather.presentation.manage_cities

import android.app.AlertDialog
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
import androidx.fragment.app.viewModels
import com.example.weather.R
import com.example.weather.databinding.FragmentManageCitiesBinding
import com.example.weather.domain.models.cities.City
import com.example.weather.presentation.main.BaseFragment
import com.example.weather.presentation.main.LoadingView
import com.example.weather.presentation.main.State
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageCitiesFragment : BaseFragment(), LoadingView {
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
        setUpActionBar()
        lockNavigationDrawer()
        observeViewModel()
        getTopCities()
        setUpEpoxyRecyclerView()
        setUpSearch()
        searchCity()

        binding.fabOpenMapFragment.setOnClickListener {
            navController.navigate(
                ManageCitiesFragmentDirections.actionManageCitiesFragmentToMapFragment(
                    currentCity
                )
            )
        }
    }

    private fun setUpSearch() {
        binding.etSearch.apply {
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchCity(viewModel.searchQuery ?: "")
                    hideKeyboard()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun getTopCities() {
        viewModel.getTopCities()
    }

    override fun setUpActionBar() {
        // change Up button icon
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left_blue)
    }

    private fun observeViewModel() {
        viewModel.currentCity.observe(viewLifecycleOwner) {
            currentCity = Gson().toJson(it, City::class.java)
        }

        viewModel.citiesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    epoxyController.topCities = state.data!!.cities
                }

                is State.Loading -> {
                    displayLoading()
                }

                is State.Error -> {
                    makeToast(requireContext(), state.message!!, Toast.LENGTH_LONG)
                    hideLoading()
                }
            }
        }

        viewModel.searchCityState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    epoxyController.searchResultCities = state.data!!.foundCities
                    // to wait until the epoxy model is updated
                    Handler(Looper.getMainLooper()).postDelayed({
                        hideLoading()
                        hideNoResultsMsg()
                    }, 500L)
                }

                is State.Error -> {
                    hideLoading()
                    displayNoResultsMsg()
                }

                is State.Loading -> {
                    hideNoResultsMsg()
                    displayLoading()
                }
            }
        }
    }

    private fun searchCity() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                editable?.let { query ->
                    if (query.isEmpty()) {
                        hideNoResultsMsg()
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
                setUpConfirmationAlertDialog(it)
            }
        )
        binding.rvCities.setController(epoxyController)
    }

    private fun setUpConfirmationAlertDialog(city: City) {
        AlertDialog.Builder(requireContext())
            .setTitle("Save selection?")
            .setMessage("You will receive weather updates for the selected city")
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.setCurrentCity(city)
                dialog.dismiss()
                navController.popBackStack(R.id.homeWeatherFragment, true)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun displayNoResultsMsg() {
        binding.rvCities.visibility = View.INVISIBLE
        binding.tvMessageNoResults.visibility = View.VISIBLE
    }

    private fun hideNoResultsMsg() {
        binding.rvCities.visibility = View.VISIBLE
        binding.tvMessageNoResults.visibility = View.INVISIBLE
    }

    override fun displayLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}