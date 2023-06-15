package com.example.weather.presentation.weather_home

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.weather.R
import com.example.weather.common.Constants
import com.example.weather.common.Utils.convertEpochToLocalDate
import com.example.weather.common.Utils.getWeatherIcon
import com.example.weather.databinding.FragmentHomeWeatherFlatBinding
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.presentation.main.BaseFragment
import com.example.weather.presentation.main.LoadingView
import com.example.weather.presentation.main.MainActivity
import com.example.weather.presentation.main.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeWeatherFragment : BaseFragment(), LoadingView {
    private var _binding: FragmentHomeWeatherFlatBinding? = null
    private val binding: FragmentHomeWeatherFlatBinding get() = _binding!!
    private val viewModel: HomeWeatherViewModel by viewModels()
    private lateinit var epoxyController: HourlyWeatherEpoxyController
    private lateinit var currentCity: City
    private lateinit var measurementUnit: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // closes the app when user taps back button
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeWeatherFlatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()
        setUpStatusBar()
        unlockNavigationDrawer()
        observeViewModel()
        setUpEpoxyRecyclerView()
        setUpMenu()
        setUpSwipeToRefresh()
    }

    override fun setUpActionBar() {
        actionBar?.apply {
            // change background color
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.white)))
            show()
        }
    }

    override fun setUpStatusBar() {
        window.apply {
            // change background color
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

            // change  text color
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true

            // change navigation bar background color
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    private fun observeViewModel() {
        viewModel.userPref.observe(viewLifecycleOwner) {
            currentCity = it.city
            measurementUnit = it.measurementUnit
            (requireActivity() as MainActivity).supportActionBar?.title = currentCity.name
            getWeatherData(currentCity, measurementUnit)
        }

        viewModel.weatherState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    epoxyController.items = state.data!!.hourly
                    bindWeatherData(state.data.current)
                }

                is State.Error -> {
                    makeToast(requireContext(), state.message?: "Unknown error", Toast.LENGTH_LONG)
                    hideLoading()
                }

                is State.Loading -> {
                    displayLoading()
                }

                else -> {
                    makeToast(requireActivity(), "Result of when branch is not of class State", Toast.LENGTH_LONG)
                    displayLoading()
                }
            }
        }
    }

    private fun setUpEpoxyRecyclerView() {
        epoxyController = HourlyWeatherEpoxyController(
            onItemClicked = {
                bindWeatherData(it)
                epoxyController.selectedItem = it
            },
            context = requireContext()
        )
        binding.rvWeatherHourly.setController(epoxyController)
    }

    private fun getWeatherData(city: City, measurementUnit: String) {
        viewModel.getWeatherData(
            latitude = city.latitude,
            longitude = city.longitude,
            measurementUnit = measurementUnit
        )
    }

    private fun bindWeatherData(currentWeather: OneHourWeather) {
        binding.layoutWeatherInfoMain.apply {
            tvWeatherName.text = currentWeather.weather.weather.weatherName
            tvDate.text = convertEpochToLocalDate(currentWeather.timeDate)
            tvTemperature.text = getString(R.string.temperature, currentWeather.temp)
            ivWeatherIcon.setImageResource(getWeatherIcon(currentWeather.weather))
        }

        binding.layoutWeatherInfoMain.cardWeatherAdditionalInfo.apply {
            tvWind.text = if (measurementUnit == Constants.MeasurementsUnits.METRIC.value) {
                getString(R.string.wind_metric, currentWeather.windSpeed.toString())
            } else {
                getString(R.string.wind_imperial, currentWeather.windSpeed.toString())
            }
            tvFeelsLike.text = getString(R.string.temperature, currentWeather.temp)
            tvIndexUv.text = currentWeather.uvi.toString()
            tvHumidity.text =
                getString(R.string.humidity, currentWeather.humidity.toString())
        }

        binding.cardNext7Days.setOnClickListener {
            navController.navigate(R.id.action_homeWeatherFragment_to_dailyWeatherFragment)
        }
    }

    private fun setUpMenu() {
        (requireActivity() as MainActivity).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home_weather, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_cities -> {
                        navController.navigate(R.id.action_homeWeatherFragment_to_citiesListFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.fetchData()
                getWeatherData(currentCity, measurementUnit)
                isRefreshing = false
            }
        }
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