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
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.common.Utils.convertEpochToLocalDate
import com.example.weather.common.Utils.getWeatherIcon
import com.example.weather.domain.models.cities.City
import com.example.weather.domain.models.weather.OneHourWeather
import com.example.weather.databinding.FragmentHomeWeatherFlatBinding
import com.example.weather.presentation.State
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeWeatherFragment : Fragment() {
    private var _binding: FragmentHomeWeatherFlatBinding? = null
    private val binding: FragmentHomeWeatherFlatBinding get() = _binding!!
    private val viewModel: HomeWeatherViewModel by viewModels()
    private lateinit var epoxyController: HourlyWeatherEpoxyController
    private lateinit var currentCity: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().apply {
            onBackPressedDispatcher.addCallback(this) {
                finish()
            }
        }
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
        observeViewModel()
        setUpActionBar()
        setUpStatusBar()
        setUpEpoxyRecyclerView()
        setUpMenu()
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                getWeatherData(currentCity)
                isRefreshing = false
            }
        }
    }

    private fun setUpActionBar() {
        (requireActivity() as MainActivity).supportActionBar?.apply {
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
            )
            show()
        }
    }

    private fun observeViewModel() {
        viewModel.currentCity.observe(viewLifecycleOwner) {
            currentCity = it
            setActionBarTitle()
            getWeatherData(currentCity)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    hideLoading()
                    val weather = state.data!!
                    epoxyController.items = weather.hourly
                    bindWeatherData(weather.current)
                }

                is State.Error -> {
                    hideLoading()
                }

                is State.Loading -> {
                    displayLoading()
                }
            }
        }
    }

    private fun setActionBarTitle() {
        (requireActivity() as MainActivity).supportActionBar?.title = currentCity.name
    }

    private fun setUpStatusBar() {
        requireActivity().window.apply {
            // change background color
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

            // change  text color
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true

            // change navigation bar background color
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
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

    private fun getWeatherData(city: City) {
        viewModel.getWeatherData(latitude = city.latitude, longitude = city.longitude)
    }

    private fun bindWeatherData(currentWeather: OneHourWeather) {
        binding.layoutWeatherInfoMain.apply {
            tvWeatherName.text = currentWeather.weather.weather.weatherName
            tvDate.text = convertEpochToLocalDate(currentWeather.timeDate)
            tvTemperature.text =
                getString(R.string.temperature, currentWeather.temp.toString())
            ivWeatherIcon.setImageResource(getWeatherIcon(currentWeather.weather))
        }

        binding.layoutWeatherInfoMain.cardWeatherAdditionalInfo.apply {
            tvWind.text = getString(R.string.wind, currentWeather.windSpeed.toString())
            tvFeelsLike.text = getString(
                R.string.temperature,
                currentWeather.feelsLikeTemperature.toString()
            )
            tvIndexUv.text = currentWeather.uvi.toString()
            tvHumidity.text =
                getString(R.string.humidity, currentWeather.humidity.toString())
        }

        binding.tvNext7Days.setOnClickListener {
            findNavController().navigate(R.id.action_homeWeatherFragment_to_dailyWeatherFragment)
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
                        findNavController().navigate(R.id.action_homeWeatherFragment_to_citiesListFragment)
                        true
                    }

                    R.id.menu_item_user_profile -> {
                        findNavController().navigate(R.id.action_homeWeatherFragment_to_userProfileFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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